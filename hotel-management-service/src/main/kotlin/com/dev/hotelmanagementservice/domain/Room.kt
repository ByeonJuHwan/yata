package com.dev.hotelmanagementservice.domain

import com.dev.hotelmanagementservice.domain.id.HotelId
import com.dev.hotelmanagementservice.domain.id.RoomId
import com.dev.hotelmanagementservice.domain.status.RoomStatus
import com.dev.hotelmanagementservice.domain.status.RoomType
import com.github.f4b6a3.ulid.UlidCreator
import java.math.BigDecimal
import java.time.LocalDateTime

class Room private constructor(
    val id: RoomId,
    val hotelId: HotelId,
    val roomName: RoomName,
    val roomType: RoomType,
    val capacity: Capacity,
    val totalRoom: TotalRoom,
    val basePrice: Money,
    val status: RoomStatus,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
) {

    companion object {
        fun create(
            hotelId: HotelId,
            roomName: String,
            roomType: String,
            capacity: Int,
            totalRoom: Int,
            basePrice: BigDecimal,
            currency: String = "KRW",
        ): Room {
            return Room(
                id = RoomId(UlidCreator.getUlid().toString()),
                hotelId = hotelId,
                roomName = RoomName(roomName),
                roomType = RoomType.valueOf(roomType),
                capacity = Capacity(capacity),
                totalRoom = TotalRoom(totalRoom),
                basePrice = Money(basePrice, currency),
                status = RoomStatus.ACTIVE,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }


        // DB에서 복원할 때 사용 (Repository에서 호출)
        fun reconstitute(
            id: RoomId,
            hotelId: HotelId,
            roomName: RoomName,
            roomType: RoomType,
            capacity: Capacity,
            totalRoom: TotalRoom,
            status: RoomStatus,
            basePrice: Money,
            createdAt: LocalDateTime?,
            updatedAt: LocalDateTime?,
        ): Room {
            return Room(
                id = id,
                hotelId = hotelId,
                roomName = roomName,
                roomType = roomType,
                capacity = capacity,
                totalRoom = totalRoom,
                basePrice = basePrice,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}

data class Money(
    val amount: BigDecimal,
    val currency: String = "KRW"
) {
    init {
        require(amount >= BigDecimal.ZERO) { "Amount must be non-negative" }
        require(currency.length == 3) { "Currency must be 3-letter code" }
    }
}


data class RoomName(val roomName: String) {
    init {
        require(roomName.isNotBlank()) { "방 이름은 빈값일수 없습니다" }
        require(roomName.length <= 20) { "방 이름은 20자 이내로 입력해야 합니다." }
    }
}

data class Capacity(val capacity: Int) {
    init {
        require(capacity > 0) { "수용인원은 0보다 커야합니다" }
        require(capacity <= 10) { "수용인원은 10보다 크거나작아야 합니다" }
    }
}

data class TotalRoom(val totalRoom: Int) {
    init {
        require(totalRoom >0) {"방의 총 개수는 0보다 커야합니다"}
    }
}