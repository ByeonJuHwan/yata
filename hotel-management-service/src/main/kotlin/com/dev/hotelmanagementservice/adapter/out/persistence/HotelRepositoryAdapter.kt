package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.domain.Hotel
import org.springframework.stereotype.Component

@Component
class HotelRepositoryAdapter : HotelRepository {
    override fun saveHotel(hotel: Hotel) {
        TODO("Not yet implemented")
    }
}