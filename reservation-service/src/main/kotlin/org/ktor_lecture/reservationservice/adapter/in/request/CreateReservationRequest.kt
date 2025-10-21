package org.ktor_lecture.reservationservice.adapter.`in`.request

data class CreateReservationRequest(
    val roomId: String,
    val hotelId: String,
)
