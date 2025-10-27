package org.ktor_lecture.reservationservice.application.service

import org.ktor_lecture.reservationservice.adapter.`in`.request.CreateReservationRequest
import org.ktor_lecture.reservationservice.application.port.`in`.CreateReservationUseCase
import org.ktor_lecture.reservationservice.application.port.out.ReservationRepository
import org.ktor_lecture.reservationservice.application.port.out.RoomInventoryClient
import org.ktor_lecture.reservationservice.application.port.http.request.DeductInventoryRequest
import org.ktor_lecture.reservationservice.application.port.out.PaymentClient
import org.ktor_lecture.reservationservice.domain.Reservation
import org.springframework.stereotype.Service

@Service
class ReservationService (
    private val reservationRepository: ReservationRepository,
    private val roomInventoryClient: RoomInventoryClient,
    private val paymentClient: PaymentClient,
) : CreateReservationUseCase {

    override fun createReservation(userId: String, request: CreateReservationRequest) {
        // 재고감소
        val deductInventoryResponse = roomInventoryClient.deductInventory(DeductInventoryRequest(request.roomId, request.date))
        // 결제
        val paymentResponse = paymentClient.pay(request.roomId, request.amount)
        // 예약생성

        val reservation = Reservation.create(
            userId = userId,
            roomId = request.roomId,
            hotelId = request.hotelId,
        )

        reservationRepository.save(reservation)
    }
}