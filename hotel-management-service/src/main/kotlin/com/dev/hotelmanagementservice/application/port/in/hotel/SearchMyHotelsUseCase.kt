package com.dev.hotelmanagementservice.application.port.`in`.hotel

import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchMyHotelResponse

interface SearchMyHotelsUseCase {
    fun getMyHotels(userId: String) : SearchMyHotelResponse
}