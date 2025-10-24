package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.RoomInventory

interface RoomInventoryRepository {
    fun saveAll(roomInventories: kotlin.collections.List<com.dev.hotelmanagementservice.domain.RoomInventory>)
}