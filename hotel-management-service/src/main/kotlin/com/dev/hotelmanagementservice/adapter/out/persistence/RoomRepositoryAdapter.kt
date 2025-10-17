package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.RoomJpaRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.domain.Room
import org.springframework.stereotype.Component

@Component
class RoomRepositoryAdapter (
    private val roomJpaRepository: RoomJpaRepository,
) : RoomRepository {

    override fun findByHotelId(hotelId: String): List<Room> {
        return roomJpaRepository.findByHotelId(hotelId)
            .map { it.toDomain() }
    }

    override fun save(room: Room) {
        val roomEntity = RoomEntity.from(room)
        roomJpaRepository.save(roomEntity)
    }

    override fun findAll(): List<Room> {
        return roomJpaRepository.findAll()
            .map { it.toDomain() }
    }
}