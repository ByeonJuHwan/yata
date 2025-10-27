package org.ktor_lecture.reservationservice.adapter.out.client

import org.ktor_lecture.reservationservice.application.port.out.RoomInventoryClient
import org.ktor_lecture.reservationservice.application.port.http.request.DeductInventoryRequest
import org.ktor_lecture.reservationservice.application.port.http.request.IncreaseInventoryRequest
import org.ktor_lecture.reservationservice.application.port.http.response.RoomDeductResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Component
@FeignClient(
    name = "HOTEL-MANAGEMENT-SERVICE",
)
interface RoomInventoryFeignClient : RoomInventoryClient {

    @PostMapping("/internal/api/v1/rooms/deduct")
    override fun deductInventory(@RequestBody request: DeductInventoryRequest): RoomDeductResponse


    @PostMapping("/internal/api/v1/rooms/increase")
    override fun increaseInventory(@RequestBody request: IncreaseInventoryRequest)

}