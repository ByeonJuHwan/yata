package org.ktor_lecture.reservationservice.application.service

import org.ktor_lecture.reservationservice.adapter.`in`.request.CreateReservationRequest
import org.ktor_lecture.reservationservice.application.port.`in`.CreateReservationUseCase
import org.ktor_lecture.reservationservice.application.port.out.ReservationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService (
    private val reservationRepository: ReservationRepository,
) : CreateReservationUseCase {

    @Transactional
    override fun createReservation(userId: String, request: CreateReservationRequest) {
        //
    }
}