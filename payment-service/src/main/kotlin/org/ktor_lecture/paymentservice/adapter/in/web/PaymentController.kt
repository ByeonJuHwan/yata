package org.ktor_lecture.paymentservice.adapter.`in`.web

import org.ktor_lecture.paymentservice.adapter.`in`.request.PaymentRequest
import org.ktor_lecture.paymentservice.adapter.`in`.response.PaymentResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController {

    @PostMapping("/pay")
    fun pay(
        @RequestBody request: PaymentRequest,
    ): PaymentResponse {
        println(request.amount)
        return PaymentResponse("se")
    }
}