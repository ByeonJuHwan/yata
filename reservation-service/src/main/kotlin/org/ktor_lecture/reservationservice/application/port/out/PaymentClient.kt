package org.ktor_lecture.reservationservice.application.port.out

import org.ktor_lecture.reservationservice.application.port.http.request.PaymentRequest
import org.ktor_lecture.reservationservice.application.port.http.request.RefundPaymentRequest
import org.ktor_lecture.reservationservice.application.port.http.response.PaymentResponse

interface PaymentClient {
    fun pay(request: PaymentRequest): PaymentResponse
    fun refund(request: RefundPaymentRequest)
}