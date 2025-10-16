package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.Room

interface RoomRepository {
    fun save(room: Room)

    fun findAll(): List<Room>
}