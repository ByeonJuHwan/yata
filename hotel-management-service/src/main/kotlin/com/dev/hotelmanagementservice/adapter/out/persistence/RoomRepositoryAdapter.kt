package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.RoomJpaRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.domain.Room
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class RoomRepositoryAdapter (
    private val roomJpaRepository: RoomJpaRepository,
) : RoomRepository {

    override fun findById(roomId: String): Optional<Room> {
        return roomJpaRepository.findById(roomId)
            .map { it.toDomain() }

    }

    override fun findByHotelId(hotelId: String): List<Room> {
        return roomJpaRepository.findByHotelId(hotelId)
            .map { it.toDomain() }
    }

    override fun save(room: Room): Room {
        val roomEntity = RoomEntity.from(room)
        return roomJpaRepository.save(roomEntity).toDomain()
    }

    override fun findAll(): List<Room> {
        return roomJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun getRoomStockWithLock(roomId: String): Room? {
        return roomJpaRepository.findByIdWithLock(roomId)?.toDomain()
    }

    override fun deductStock(room: Room) {
        return roomJpaRepository.updateStock(room.id.value, room.stock.stock)
    }
}