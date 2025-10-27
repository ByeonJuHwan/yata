package org.ktor_lecture.reservationservice.application.port.http.request

data class RefundPaymentRequest (
    val paymentId: String,
)