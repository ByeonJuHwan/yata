package org.ktor_lecture.reservationservice.application.port.http.request

data class DeductInventoryRequest (
    val roomId: String,
    val date: String,
)