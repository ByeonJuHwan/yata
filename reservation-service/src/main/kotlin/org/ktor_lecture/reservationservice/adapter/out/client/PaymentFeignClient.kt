package org.ktor_lecture.reservationservice.adapter.out.client

import org.ktor_lecture.reservationservice.application.port.http.request.PaymentRequest
import org.ktor_lecture.reservationservice.application.port.http.response.PaymentResponse
import org.ktor_lecture.reservationservice.application.port.out.PaymentClient
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.math.BigDecimal

@Component
@FeignClient(
    name = "PAYMENT-SERVICE",
)
interface PaymentFeignClient : PaymentClient {

    @PostMapping("/api/v1/payments/pay")
    override fun pay(@RequestBody request: PaymentRequest): PaymentResponse
}