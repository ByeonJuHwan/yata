package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.Room
import java.util.Optional

interface RoomRepository {

    fun findById(roomId: String): Optional<Room>

    fun findByHotelId(hotelId: String): List<Room>

    fun save(room: Room): Room

    fun findAll(): List<Room>

    fun deleteAll()
}