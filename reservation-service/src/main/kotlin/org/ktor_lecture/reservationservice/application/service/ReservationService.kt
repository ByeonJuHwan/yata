package org.ktor_lecture.reservationservice.application.service

import org.ktor_lecture.reservationservice.adapter.`in`.request.CreateReservationRequest
import org.ktor_lecture.reservationservice.application.port.`in`.CreateReservationUseCase
import org.ktor_lecture.reservationservice.application.port.out.ReservationRepository
import org.springframework.stereotype.Service

@Service
class ReservationService (
    private val reservationRepository: ReservationRepository,
) : CreateReservationUseCase {

    override fun createReservation(userId: String, request: CreateReservationRequest) {
        // 결제 먼저 진행 -> 결제 서비스에 요청

        // 재고 감소 진행

        // 예약 생성 -> 트랜잭션으로 만약 예약 실행이 안되면 이 범위만 트랜잭션 추가

        // 여기서는 예약 완료 이벤트 발행 -> 알림
    }
}