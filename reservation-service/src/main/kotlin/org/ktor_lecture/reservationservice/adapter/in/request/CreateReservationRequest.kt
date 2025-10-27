package org.ktor_lecture.reservationservice.adapter.`in`.request

import java.math.BigDecimal

data class CreateReservationRequest(
    val hotelId: String,
    val roomId: String,
    val date: String,
    val amount: BigDecimal,
)
