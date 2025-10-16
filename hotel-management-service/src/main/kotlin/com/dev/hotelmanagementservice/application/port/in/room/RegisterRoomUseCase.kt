package com.dev.hotelmanagementservice.application.port.`in`.room

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest

interface RegisterRoomUseCase {
    fun registerRoom (request: RegisterRoomRequest)
}