package org.ktor_lecture.reservationservice.application.port.http.request

import java.math.BigDecimal

data class PaymentRequest(
    val roomId: String,
    val amount: BigDecimal,
)
