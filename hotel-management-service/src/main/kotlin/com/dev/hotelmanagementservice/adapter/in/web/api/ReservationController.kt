package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.application.port.`in`.reservation.CreateReservationUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/reservation")
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