# 분산 트랜잭션과 Saga 패턴

MSA(Microservices Architecture)로 프로젝트를 진행하면서 가장 고민한 부분은 **분산 트랜잭션**입니다.

모놀리식 아키텍처에서는 단일 데이터베이스를 사용하므로, 예외 발생 시 `@Transactional` 어노테이션을 통해 
전체 작업을 롤백하여 데이터 정합성을 보장할 수 있었습니다.

하지만 MSA처럼 서로 다른 서비스가 독립적인 데이터베이스를 가지고 통신하는 구조에서는, 
기존의 ACID 트랜잭션 방식만으로는 데이터 정합성을 보장하기 어렵습니다.

## 문제 상황

예약 프로세스는 다음과 같이 여러 서비스가 협력하여 진행됩니다

```kotlin
// 예약 서비스 (Reservation Service)
fun createReservation(request: ReservationRequest) {
    // 1. 호텔 서비스와 HTTP 통신 -> 방 재고 감소
    hotelClient.decreaseRoomInventory()
    
    // 2. 결제 서비스와 HTTP 통신 -> 결제 처리
    paymentClient.processPayment()
    
    // 3. 예약 정보 저장 (자체 DB)
    reservationRepository.save()
    
    // 4. 예약 완료 이벤트 발행 -> 예약 확인 알림 발송
    eventPublisher.reservationComplete()
}
```

### 모놀리식의 경우
```kotlin
@Transactional
fun createReservation(request: ReservationRequest) {
    roomRepository.decreaseInventory()      // 같은 DB
    paymentRepository.savePayment()         // 같은 DB
    reservationRepository.save()            // 같은 DB
    
    // 어느 단계에서든 예외 발생 시 전체 롤백 -> 데이터 정합성 보장 ✅
}
```

### MSA의 문제점
```kotlin
fun createReservation(request: ReservationRequest) {
    // 1. 호텔 서비스 호출 -> 재고 감소 성공 ✅
    hotelClient.decreaseRoomInventory() 
    // (호텔 서비스의 DB에 커밋됨)
    
    // 2. 결제 서비스 호출 -> 결제 성공 ✅
    paymentClient.processPayment()
    // (결제 서비스의 DB에 커밋됨)
    
    // 3. 예약 저장 실패! ❌
    reservationRepository.save() 
    // -> DatabaseException 발생
    
    // 문제 발생!
    // - 방 재고는 이미 감소됨 (다른 서비스의 DB)
    // - 결제도 이미 완료됨 (다른 서비스의 DB)
    // - 하지만 예약은 생성되지 않음
    // -> 고객은 돈을 지불했지만 예약이 안 된 상황 발생!
}
```

**각 서비스는 독립적인 데이터베이스를 사용하므로, 한 서비스에서 예외가 발생해도 
이미 커밋된 다른 서비스의 작업은 자동으로 롤백되지 않습니다.**

## 어떻게 해결할수 있을까?

이를 해결하기 위해서는 각 서비스에 **보상 트랜잭션(Compensating Transaction)** 을 
구현하여 명시적으로 롤백 로직을 실행하는 방법을 사용했습니다.

```kotlin
    override fun createReservation(userId: String, request: CreateReservationRequest) {
        var isInventoryDeducted = false
        var isPaymentMade = false
        var roomInventoryId = ""
        var paymentId = ""

        try {
            // 재고감소
            val deductInventoryResponse = roomInventoryClient.deductInventory(DeductInventoryRequest(request.roomId, request.date))
            roomInventoryId = deductInventoryResponse.roomInventoryId
            isInventoryDeducted = true

            // 결제
            val paymentResponse = paymentClient.pay(PaymentRequest(request.roomId, request.amount))
            paymentId = paymentResponse.paymentId
            isPaymentMade = true

            // 예약생성
            val reservation = Reservation.create(
                userId = userId,
                roomId = request.roomId,
                hotelId = request.hotelId,
            )
            reservationRepository.save(reservation)
            
        } catch (e: Exception) {
            if (isPaymentMade) {
                logger.info("결제 롤백")
                paymentClient.refund(RefundPaymentRequest(paymentId))
            }

            if (isInventoryDeducted) {
                logger.info("재고 롤백")
                roomInventoryClient.increaseInventory(IncreaseInventoryRequest(roomInventoryId))
            }

            throw e
        }
    }
```

비즈니스 로직상 각 서비스 간 의존성이 강하거나 순서가 중요할 때는 
역순으로 보상 트랜잭션을 실행하는 것이 필수입니다.

현재 예약 로직에서는 재고 복구와 결제 취소가 각각 독립적으로 
실행되어도 문제가 없기 때문에 역순이 필수적이지는 않습니다. 
하지만 시간의 흐름에 따른 직관성과 코드 가독성 측면에서 
역순으로 보상 트랜잭션을 작성하는 것이 일반적이므로, 
이 관례를 따라 역순으로 구현했습니다.

### 현재 설계의 문제점

보상 트랜잭션을 통해 MSA 환경에서 예외 발생 시에도 데이터 정합성을 맞추도록 설계했습니다. 

하지만 이 방식은 **네트워크 장애에 취약**합니다.

다음 예시를 통해 어떤부분이 취약점인지 그리고 어떻게 해결했는지 보겠습니다.

#### 문제 시나리오
```kotlin
fun createReservation(request: ReservationRequest) {
    try {
        hotelService.decreaseInventory()   // ✅ 성공
        paymentService.processPayment()    // ✅ 성공
        reservationRepository.save()       // ❌ 실패
        
    } catch (e: Exception) {
        // 보상 트랜잭션 시도
        paymentService.cancelPayment()     // ✅ 성공
        hotelService.increaseInventory()   // ❌ 네트워크 타임아웃!
        
        // 문제: 재고는 복구되지 않고, 결제만 취소된 상태
    }
}
```

호텔 서비스에 재고 복구 요청을 보내는 시점에 다음과 같은 상황이 발생할 수 있습니다:

- **서버 장애**: 호텔 서비스가 다운되어 응답 불가
- **네트워크 장애**: 통신 자체가 불가능
- **타임아웃**: 과부하로 인한 응답 지연
- **부분 실패**: 요청은 전달되었으나 응답을 받지 못함

이 경우 보상 트랜잭션이 실패하여 **데이터 정합성이 깨지게 됩니다.**

#### 해결 방안: Saga 상태 관리

이를 해결하기 위해 **Saga 실행 상태를 DB에 저장**하고, 
실패한 보상 트랜잭션을 재시도하는 방식으로 개선하겠습니다.


