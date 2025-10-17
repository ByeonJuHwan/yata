package com.dev.hotelmanagementservice.adapter.out.persistence.jpa

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomJpaRepository : JpaRepository<RoomEntity, String> {
    fun findByHotelId(hotelId: String): List<RoomEntity>

}