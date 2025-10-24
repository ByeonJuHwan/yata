package com.dev.hotelmanagementservice.adapter.out.persistence.jpa

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomInventoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoomInventoryJpaRepository : JpaRepository<RoomInventoryEntity, String> {
}