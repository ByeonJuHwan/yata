package org.ktor_lecture.reservationservice.adapter.`in`.api

import org.ktor_lecture.reservationservice.adapter.`in`.request.CreateReservationRequest
import org.ktor_lecture.reservationservice.application.port.`in`.CreateReservationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reservations")
class ReservationController (
    private val createReservationUseCase: CreateReservationUseCase,
) {

    @PostMapping
    fun createReservation(
        @RequestHeader("X-USER-ID") userId: String,
        @RequestBody request: CreateReservationRequest,
    ) : ResponseEntity<Void> {
        createReservationUseCase.createReservation(userId, request)
        return ResponseEntity(HttpStatus.CREATED)
    }
}