package com.dev.hotelmanagementservice.adapter.out.persistence.jpa

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.HotelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface HotelJpaRepository : JpaRepository<HotelEntity, String> {

    fun findByOwnerId(ownerId: String): List<HotelEntity>
}