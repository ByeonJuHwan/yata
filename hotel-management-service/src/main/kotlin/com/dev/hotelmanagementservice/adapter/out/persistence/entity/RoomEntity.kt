package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.BedType
import com.dev.hotelmanagementservice.domain.RoomStatus
import com.dev.hotelmanagementservice.domain.RoomType
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
    val capacity: Int, // 수용 가능 인원

    @Column(name = "base_price", nullable = false, precision = 19, scale = 2)
    val basePrice: BigDecimal,

    @Enumerated(EnumType.STRING)
    @Column(name = "bed_type", nullable = false, length = 20)
    val bedType: BedType,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    val status: RoomStatus,

) : BaseTimeEntity() {

}