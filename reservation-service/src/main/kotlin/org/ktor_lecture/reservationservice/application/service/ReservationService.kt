package org.ktor_lecture.reservationservice.application.service

import org.ktor_lecture.reservationservice.adapter.`in`.request.CreateReservationRequest
import org.ktor_lecture.reservationservice.application.port.`in`.CreateReservationUseCase
import org.ktor_lecture.reservationservice.application.port.out.ReservationRepository
import org.ktor_lecture.reservationservice.application.port.out.RoomInventoryClient
import org.ktor_lecture.reservationservice.application.port.http.request.DeductInventoryRequest
import org.ktor_lecture.reservationservice.application.port.http.request.IncreaseInventoryRequest
import org.ktor_lecture.reservationservice.application.port.http.request.PaymentRequest
import org.ktor_lecture.reservationservice.application.port.http.request.RefundPaymentRequest
import org.ktor_lecture.reservationservice.application.port.out.PaymentClient
import org.ktor_lecture.reservationservice.domain.Reservation
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class ReservationService (
    private val reservationRepository: ReservationRepository,
    private val roomInventoryClient: RoomInventoryClient,
    private val paymentClient: PaymentClient,
) : CreateReservationUseCase {

    private val logger = LoggerFactory.getLogger(ReservationService::class.java)

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

            throw e // TODO 커스텀 예외로 변경
        }
    }
}