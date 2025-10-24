package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.Capacity
import com.dev.hotelmanagementservice.domain.Money
import com.dev.hotelmanagementservice.domain.Room
import com.dev.hotelmanagementservice.domain.RoomName
import com.dev.hotelmanagementservice.domain.TotalRoom
import com.dev.hotelmanagementservice.domain.id.HotelId
import com.dev.hotelmanagementservice.domain.id.RoomId
import com.dev.hotelmanagementservice.domain.status.RoomStatus
import com.dev.hotelmanagementservice.domain.status.RoomType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "rooms")
class RoomEntity(
    @Id
    @Column(name = "room_id", length = 26)
    val id: String,

    @Column(name = "hotel_id", nullable = false, length = 26)
    val hotelId: String,

    @Column(name = "room_name", nullable = false, length = 20)
    val roomName: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false, length = 20)
    val roomType: RoomType,

    @Column(name = "capacity", nullable = false)
    val capacity: Int,

    @Column(name = "total_rooms", nullable = false)
    val totalRoom: Int,

    @Column(name = "base_price", nullable = false, precision = 19, scale = 2)
    val basePrice: BigDecimal,

    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val status: RoomStatus,

    ) : BaseTimeEntity() {

    fun toDomain(): Room {
        return Room.reconstitute(
            id = RoomId(this.id),
            hotelId = HotelId(this.hotelId),
            roomName = RoomName(this.roomName),
            roomType = this.roomType,
            capacity = Capacity(this.capacity),
            totalRoom = TotalRoom(this.totalRoom),
            basePrice = Money(this.basePrice),
            status = this.status,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt
        )
    }

    companion object {
        fun from(room: Room): RoomEntity {
            return RoomEntity(
                id = room.id.roomId,
                hotelId = room.hotelId.hotelId,
                roomName = room.roomName.roomName,
                roomType = room.roomType,
                capacity = room.capacity.capacity,
                totalRoom = room.totalRoom.totalRoom,
                basePrice = room.basePrice.amount,
                status = room.status,
            )
        }
    }

}