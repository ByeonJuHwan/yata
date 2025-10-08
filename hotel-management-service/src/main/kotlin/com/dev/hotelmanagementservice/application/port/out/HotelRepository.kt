package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.Hotel

interface HotelRepository {

    fun saveHotel(hotel: Hotel)
    fun findAll(): List<Hotel>
    fun findByOwnerId(userId: String): List<Hotel>
}