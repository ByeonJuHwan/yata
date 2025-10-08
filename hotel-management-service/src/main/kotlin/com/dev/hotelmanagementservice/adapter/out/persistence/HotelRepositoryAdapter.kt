package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.HotelEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.HotelJpaRepository
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.domain.Hotel
import org.springframework.stereotype.Component

@Component
class HotelRepositoryAdapter (
    private val hotelJpaRepository: HotelJpaRepository,
) : HotelRepository {

    override fun saveHotel(hotel: Hotel) {
        val hotelEntity = HotelEntity.from(hotel)
        hotelJpaRepository.save(hotelEntity)
    }

    override fun findAll(): List<Hotel> {
        return hotelJpaRepository.findAll()
            .map { it.toDomain() }

    }
}