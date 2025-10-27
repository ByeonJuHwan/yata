package org.ktor_lecture.reservationservice.adapter.out.client

import org.ktor_lecture.reservationservice.application.port.out.PaymentClient
import org.springframework.stereotype.Component

@Component
interface PaymentFeignClient : PaymentClient {

}