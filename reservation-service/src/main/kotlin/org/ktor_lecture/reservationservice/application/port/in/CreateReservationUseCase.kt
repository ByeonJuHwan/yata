package org.ktor_lecture.reservationservice.application.port.`in`

import org.ktor_lecture.reservationservice.adapter.`in`.request.CreateReservationRequest

interface CreateReservationUseCase {
    fun createReservation(userId: String, request: CreateReservationRequest)

}