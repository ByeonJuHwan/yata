package org.ktor_lecture.reservationservice.application.port.out

import org.ktor_lecture.reservationservice.application.port.http.response.PaymentResponse
import java.math.BigDecimal

interface PaymentClient {
    fun pay(roomId: String, amount: BigDecimal): PaymentResponse
}