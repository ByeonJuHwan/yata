package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomInventoryEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.RoomInventoryJpaRepository
import com.dev.hotelmanagementservice.application.port.out.RoomInventoryRepository
import com.dev.hotelmanagementservice.domain.RoomInventory
import org.springframework.stereotype.Component

@Component
class RoomInventoryAdapter (
    private val roomInventoryJpaRepository: RoomInventoryJpaRepository,
) : RoomInventoryRepository {

    override fun saveAll(roomInventories: List<RoomInventory>) {
        val roomInventoryEntities = roomInventories.map { RoomInventoryEntity.from(it) }
        roomInventoryJpaRepository.saveAll(roomInventoryEntities)
    }
}