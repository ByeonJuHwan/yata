package org.ktor_lecture.paymentservice.adapter.`in`.request

import java.math.BigDecimal

data class PaymentRequest (
    val roomId: String,
    val amount: BigDecimal,
)