package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomInventoryEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.RoomInventoryJpaRepository
import com.dev.hotelmanagementservice.application.port.out.RoomInventoryRepository
import com.dev.hotelmanagementservice.domain.RoomInventory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.Optional

@Component
class RoomInventoryAdapter (
    private val roomInventoryJpaRepository: RoomInventoryJpaRepository,
) : RoomInventoryRepository {

    override fun saveAll(roomInventories: List<RoomInventory>) {
        val roomInventoryEntities = roomInventories.map { RoomInventoryEntity.from(it) }
        roomInventoryJpaRepository.saveAll(roomInventoryEntities)
    }

    override fun findById(roomId: String): Optional<RoomInventory> {
        val roomInventoryEntity = roomInventoryJpaRepository.findById(roomId)
        return roomInventoryEntity.map { it.toDomain() }
    }

    override fun findByIdAndDateWithLock(roomId: String, date: String): RoomInventory? {
        val roomInventory = roomInventoryJpaRepository.findByIdAndDateWithLock(roomId, LocalDate.parse(date))
        return roomInventory?.toDomain()
    }

    override fun updateAvailableCount(roomInventory: RoomInventory) {
        roomInventoryJpaRepository.updateAvailableCount(RoomInventoryEntity.from(roomInventory))
    }
}