package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest
import com.dev.hotelmanagementservice.application.port.`in`.room.RegisterRoomUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/rooms")
class RoomController (
    private val registerRoomUserCase: RegisterRoomUseCase,
) {

    @PostMapping
    fun registerRoom(
        @RequestBody request: RegisterRoomRequest,
    ) : ResponseEntity<Void> {
        registerRoomUserCase.registerRoom(request)
        return ResponseEntity.ok().build()
    }

}