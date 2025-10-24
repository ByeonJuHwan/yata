package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.AvailableCount
import com.dev.hotelmanagementservice.domain.Money
import com.dev.hotelmanagementservice.domain.RoomInventory
import com.dev.hotelmanagementservice.domain.id.RoomId
import com.dev.hotelmanagementservice.domain.id.RoomInventoryId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "room_inventory")
class RoomInventoryEntity (

    @Id
    val roomInventoryId: String,

    @Column(name = "room_id", nullable = false, length = 26)
    val roomId: String,

    @Column(name = "date", nullable = false)
    val date: LocalDate,

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    val price: BigDecimal,

    @Column(name = "available_count", nullable = false)
    val availableCount: Int,
) : BaseTimeEntity() {

    fun toDomain(): RoomInventory {
        return RoomInventory.reconstitute(
            id = RoomInventoryId(roomInventoryId),
            roomId = RoomId(roomId),
            date = date,
            price = Money(price),
            availableCount = AvailableCount(availableCount),
        )
    }
    
    companion object {
        fun from(roomInventory: RoomInventory): RoomInventoryEntity {
            return RoomInventoryEntity(
                roomInventoryId = roomInventory.id.roomInventoryId,
                roomId = roomInventory.roomId.roomId,
                date = roomInventory.date,
                price = roomInventory.price.amount,
                availableCount = roomInventory.availableCount.availableCount,
            )
        }
    }
}