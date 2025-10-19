package com.dev.hotelmanagementservice.application.port.`in`.reservation

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest

interface CreateReservationUseCase {
    fun createReservation(userId: String, request: CreateReservationRequest)

}