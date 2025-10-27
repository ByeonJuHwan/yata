package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.RoomInventory
import java.util.Optional

interface RoomInventoryRepository {
    fun saveAll(roomInventories: List<RoomInventory>)
    fun findById(roomId: String): Optional<RoomInventory>
    fun findByIdAndDateWithLock(roomId: String, date: String): RoomInventory?
    fun updateAvailableCount(roomInventory: RoomInventory)
}