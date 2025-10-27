package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.RoomInventory
import java.util.Optional

interface RoomInventoryRepository {
    fun saveAll(roomInventories: kotlin.collections.List<com.dev.hotelmanagementservice.domain.RoomInventory>)
    fun findById(roomId: String): Optional<RoomInventory>
}