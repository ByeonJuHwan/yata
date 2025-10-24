package com.dev.hotelmanagementservice.domain

import com.dev.hotelmanagementservice.domain.id.RoomId
import com.dev.hotelmanagementservice.domain.id.RoomInventoryId
import com.github.f4b6a3.ulid.UlidCreator
import java.time.LocalDate

class RoomInventory private constructor(
    val id: RoomInventoryId,
    val roomId: RoomId,
    val date: LocalDate,
    val price: Money,
    val availableCount: AvailableCount,
) {
    companion object {
        fun create(
            roomId: RoomId,
            date: LocalDate,
            price: Money,
            availableCount: AvailableCount,
        ) : RoomInventory {
            return RoomInventory(
                id = RoomInventoryId(UlidCreator.getUlid().toString()),
                roomId = roomId,
                date = date,
                price = price,
                availableCount = availableCount,
            )
        }

        fun createFor30Days(
            roomId: RoomId,
            startDate: LocalDate = LocalDate.now(),
            price: Money,
            availableCount: AvailableCount,
        ) : List<RoomInventory> {
            return (0 until 30).map {
                dayOffset -> create(
                    roomId =  roomId,
                    date = startDate.plusDays(dayOffset.toLong()),
                    price = price,
                    availableCount = availableCount,
                )
            }
        }

        fun reconstitute(
            id: RoomInventoryId,
            roomId: RoomId,
            date: LocalDate,
            price: Money,
            availableCount: AvailableCount,
        ) : RoomInventory {
            return RoomInventory(
                id = id,
                roomId = roomId,
                date = date,
                price = price,
                availableCount = availableCount,
            )
        }
    }
}

data class AvailableCount(val availableCount: Int) {
    init {
        require(availableCount >= 0) { "방 잔여 개수는 양수여야 합니다" }
    }
}
