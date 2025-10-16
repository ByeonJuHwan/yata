package com.dev.hotelmanagementservice.application.port.`in`.hotel

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest

interface RegisterHotelUseCase {
    fun registerHotel(userId: String, request: RegisterHotelRequest)
}