package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest
import com.dev.hotelmanagementservice.adapter.`in`.web.response.DeductRoomInventoryResponse
import com.dev.hotelmanagementservice.application.port.`in`.room.DeductRoomInventoryUseCase
import com.dev.hotelmanagementservice.application.port.`in`.room.RegisterRoomUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rooms")
class RoomController (
    private val registerRoomUseCase: RegisterRoomUseCase,
    private val deductRoomInventoryUserCase: DeductRoomInventoryUseCase,
) {

    @PostMapping
    fun registerRoom(
        @RequestBody request: RegisterRoomRequest,
    ) : ResponseEntity<Void> {
        registerRoomUseCase.registerRoom(request)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/deduct")
    fun deductRoomInventory (
        @RequestBody request: CreateReservationRequest,
    ): DeductRoomInventoryResponse {
        return deductRoomInventoryUserCase.deductRoomInventory(request)
    }
}