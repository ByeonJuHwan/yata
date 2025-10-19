# 동시성 테스트에서 `@Transactional`로 인한 데이터 가시성 문제 해결

## 🎯 문제 상황

호텔 예약 시스템을 개발하면서 **재고 관리의 동시성 문제**를 해결해야 했습니다. 
여러 사용자가 동시에 같은 방을 예약할 때, 재고가 정확하게 차감되는지 검증하기 위해 통합 테스트를 작성했습니다.

### 비즈니스 요구사항
- 재고가 10개인 방에 100명이 동시에 예약 시도
- 정확히 10명만 예약 성공, 90명은 실패해야 함
- 최종 재고는 0이어야 함

### 초기 구현

여러가지 락 사용법이 있지만 이중에서 저는 비관적 락을 사용해서 


```kotlin
@SpringBootTest
@Transactional  // ⚠️ 문제의 원인
class ReservationConcurrencyTest {
    
    @Test
    fun `재고 10개_동시 100명 예약_10명만 성공`() {
        // given: 재고 10개인 방 생성
        val room = Room.create(stock = 10, ...)
        val savedRoom = roomRepository.save(room)
        
        // when: 100개 스레드에서 동시 예약 시도
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(threadCount)
        val latch = CountDownLatch(threadCount)

        repeat(threadCount) { index ->
            executorService.submit {
                try {
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/reservation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(request))
                            .header("X-USER-ID", userId)
                    )
                } catch (e: Exception) {
                    println("예약 실패: ${e.message}")
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await(30, TimeUnit.SECONDS)
        executorService.shutdown()
        
        // then: 10개만 성공해야 함
        val reservations = reservationRepository.findAll()
        assertThat(reservations).hasSize(10)  // ❌ 실패!
    }
}
```

### 테스트 결과
```
예약 실패: 방정보를 찾을 수 없습니다.
예약 실패: 방정보를 찾을 수 없습니다.
예약 실패: 방정보를 찾을 수 없습니다.
...

Expected: 10
Actual: 0
```

**모든 스레드에서 "방정보를 찾을 수 없습니다" 에러가 발생**했고, 예약이 단 하나도 생성되지 않았습니다.

동시성 테스트중 `@Transactinoal` 에 대한 문제와 어떻게 해결하면 좋은지 알아보겠습니다

---

## 🔍 원인 분석

### Spring의 `@Transactional`과 ThreadLocal

문제의 핵심은 **Spring의 트랜잭션 관리 방식**에 있었습니다.

#### 1. 트랜잭션은 Thread에 바인딩된다
```kotlin
@Test
@Transactional  // 테스트 메서드 시작 시 트랜잭션 시작
fun test() {
    // [메인 스레드 - Transaction A]
    roomRepository.save(room)  // INSERT (아직 커밋 안됨)
    
    CompletableFuture.runAsync {
        // [새 스레드 - Transaction B]
        // Transaction A와 완전히 독립적!
        // 예약 생성 로직
    }
}  // 테스트 종료 시 Transaction A 롤백
```

Spring의 트랜잭션은 **ThreadLocal**을 사용하여 현재 스레드에만 바인딩됩니다. 
새로 생성된 스레드는 메인 스레드의 트랜잭션 컨텍스트를 공유하지 않습니다.

#### 2. 데이터베이스 격리 수준 (READ_COMMITTED)
```
Timeline:

T0: [메인 스레드] roomRepository.save(room) 
    → INSERT 실행 (커밋 안됨, Transaction A에만 보임)

T1: [스레드 1] reservationService.createReservation()
    → SELECT ... FROM rooms WHERE id=?
    → ❌ 데이터 없음! (Transaction A가 아직 커밋 안됨)

T2: [스레드 2] reservationService.createReservation()
    → SELECT ... FROM rooms WHERE id=?
    → ❌ 데이터 없음!

T99: [메인 스레드] 테스트 종료
     → Transaction A 롤백
     → INSERT도 취소됨
```

MySQL의 기본 격리 수준인 **READ_COMMITTED**에서는 **커밋되지 않은 데이터를 읽을 수 없습니다**. 
테스트 메서드의 트랜잭션은 테스트가 끝날 때까지 커밋되지 않으므로, 
다른 스레드에서는 저장한 Room 데이터를 볼 수 없었습니다.

#### 3. 시각화
```
┌─────────────────────────────────────────────────────────┐
│ 메인 스레드 (Test Method)                                │
│ @Transactional                                          │
├─────────────────────────────────────────────────────────┤
│ Transaction A 시작                                       │
│   ↓                                                     │
│ roomRepository.save(room)                               │
│   └─ INSERT rooms ... (UNCOMMITTED)                    │
│                                                         │
│ ┌─────────────────┐  ┌─────────────────┐               │
│ │ 스레드 1         │  │ 스레드 2         │               │
│ │ Transaction B   │  │ Transaction C   │               │
│ ├─────────────────┤  ├─────────────────┤               │
│ │ SELECT rooms    │  │ SELECT rooms    │               │
│ │ WHERE id=?      │  │ WHERE id=?      │               │
│ │   ↓             │  │   ↓             │               │
│ │ ❌ 결과 없음     │  │ ❌ 결과 없음     │               │
│ └─────────────────┘  └─────────────────┘               │
│                                                         │
│ 테스트 종료                                              │
│   ↓                                                     │
│ Transaction A 롤백 (Spring 테스트 기본 동작)             │
│   └─ INSERT 취소됨                                      │
└─────────────────────────────────────────────────────────┘
```

---

## ✅ 해결 방법

### `@Transactional` 제거 -> 명시적으로 데이터 관리

```kotlin
@SpringBootTest  // ✅ @Transactional 제거
class ReservationConcurrencyTest {
    
    @BeforeEach
    fun cleanup() {
        // ✅ 명시적으로 데이터 정리
        reservationRepository.deleteAll()
        roomRepository.deleteAll()
    }
    
    @Test
    fun `재고 10개_동시 100명 예약_10명만 성공`() {
        // given
        val room = Room.create(stock = 10, ...)
        val savedRoom = roomRepository.save(room)  // ✅ 즉시 커밋!
        
        // when: 멀티스레드 테스트
        val threadCount = 100
        val executorService = Executors.newFixedThreadPool(threadCount)
        val latch = CountDownLatch(threadCount)

        repeat(threadCount) { index ->
            executorService.submit {
                try {
                    mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/reservation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(request))
                            .header("X-USER-ID", userId)
                    )
                } catch (e: YataHotelException) {
                    // 재고 부족은 정상적인 실패
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await(30, TimeUnit.SECONDS)
        executorService.shutdown()

        // then
        assertThat(reservationRepository.findAll()).hasSize(10)  // ✅ 성공!
    }
}
```

테스트 클래스의 `@Transactional`을 제거하여 **트랜잭션 스코프를 변경**했습니다.

#### Before: `@Transactional` 있음
```
┌─────────────────────────────────────┐
│ Test Method (Single Transaction)   │
├─────────────────────────────────────┤
│ save(room) ← UNCOMMITTED            │
│                                     │
│ Thread 1, 2, 3... → ❌ 조회 실패    │
│                                     │
│ Test 종료 → ROLLBACK                │
└─────────────────────────────────────┘
```

#### After: `@Transactional` 제거
```
┌──────────┐  ┌──────────┐  ┌──────────┐
│ save()   │  │ Thread 1 │  │ Thread 2 │
│   ↓      │  │    ↓     │  │    ↓     │
│ COMMIT ✅│  │ SELECT ✅ │  │ SELECT ✅ │
└──────────┘  └──────────┘  └──────────┘
     각각 독립적인 트랜잭션
```

**변경 내용:**
- **Before**: 테스트 메서드 전체가 하나의 트랜잭션
  - `save()` 실행 시 커밋되지 않고 테스트 종료 시 롤백
  - 다른 스레드(독립적인 트랜잭션)에서는 데이터 조회 불가

- **After**: 각 Repository 메서드가 독립적인 트랜잭션
  - `save()` 호출 시 즉시 커밋
  - 모든 스레드에서 커밋된 데이터 조회 가능

---

### Before (실패)
```
예약 실패: 방정보를 찾을 수 없습니다. (100건)

성공: 0
실패: 0 (모두 예외로 실패)
실제 예약: 0건
최종 재고: 10 (변화 없음)
```

### After (성공)
```
성공: 10
실패: 90
실제 예약: 10건
최종 재고: 0

✅ 비관적 락이 정상 작동
✅ 재고 정합성 보장
✅ 동시성 제어 검증 완료
```

---

## 🎯 결론

동시성 테스트에서 `@Transactional`을 제거함으로써:

1. ✅ **데이터 가시성 문제 해결**: 모든 스레드에서 저장된 데이터 조회 가능
2. ✅ **실제 동시성 검증**: 비관적 락이 올바르게 작동하는지 확인
3. ✅ **재고 정합성 보장**: 100개 요청 중 정확히 10개만 성공

이 경험을 통해 Spring의 트랜잭션 관리 메커니즘과 데이터베이스 격리 수준에 대한 깊이 있는 이해를 얻었으며, 
**테스트 환경에 따라 적절한 트랜잭션 전략을 선택하는 것**이 중요함을 배웠습니다.