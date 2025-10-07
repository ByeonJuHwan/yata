package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.Hotel

interface HotelRepository {

    fun saveHotel(hotel: Hotel)
}