package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.application.port.`in`.reservation.CreateReservationUseCase
import com.dev.hotelmanagementservice.application.port.out.ReservationRepository
import com.dev.hotelmanagementservice.domain.Reservation
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService (
    private val reservationRepository: ReservationRepository,
) : CreateReservationUseCase {

    @Transactional
    override fun createReservation(userId: String, request: CreateReservationRequest) {
        // TODO 현재 재고가 남아있는지 확인

        // TODO Room 의 재고 차감

        // 예약생성
        val reservation = Reservation.create(
            userId = userId,
            roomId = request.roomId,
            hotelId = request.hotelId,
        )

        reservationRepository.save(reservation)
    }
}