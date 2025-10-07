package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.`in`.RegisterHotelUseCase
import org.springframework.stereotype.Service

@Service
class HotelService (

) : RegisterHotelUseCase {

    override fun registerHotel(userId: String, request: RegisterHotelRequest) {

        TODO("Not yet implemented")
    }
}