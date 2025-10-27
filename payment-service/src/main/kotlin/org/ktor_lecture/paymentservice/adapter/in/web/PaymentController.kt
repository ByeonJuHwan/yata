package org.ktor_lecture.paymentservice.adapter.`in`.web

import org.ktor_lecture.paymentservice.adapter.`in`.request.PaymentRequest
import org.ktor_lecture.paymentservice.adapter.`in`.request.RefundRequest
import org.ktor_lecture.paymentservice.adapter.`in`.response.PaymentResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/api/v1/payments")
class PaymentController {

    @PostMapping("/pay")
    fun pay(
        @RequestBody request: PaymentRequest,
    ): PaymentResponse {
        println(request.amount)
        return PaymentResponse("결제 완료 id")
    }

    @PostMapping("/refund")
    fun refund(
        @RequestBody request: RefundRequest,
    ) {
        println(request.paymentId)
    }
}