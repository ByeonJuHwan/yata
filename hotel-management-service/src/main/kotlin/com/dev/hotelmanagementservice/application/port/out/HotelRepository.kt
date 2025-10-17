package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.Hotel
import java.util.Optional

interface HotelRepository {

    fun saveHotel(hotel: Hotel)
    fun findAll(): List<Hotel>
    fun findByOwnerId(userId: String): List<Hotel>
    fun findById(hotelId: String): Optional<Hotel>
}