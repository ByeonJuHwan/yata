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
        // 1. 재고 확인 -> 재고가 있으면 -> 1개줄인다 -> 잠금상태
        // 2. 예약생성 -> 10분마다 체크 -> 결제했나안했나
        // 3. 결제하면 확정처리 / 10분 지나면 예약 삭제해버리고 재고 원복
    }
}