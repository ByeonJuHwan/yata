package org.ktor_lecture.reservationservice.application.port.http.request

data class IncreaseInventoryRequest(
    val roomInventoryId: String,
)
