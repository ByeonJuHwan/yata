package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.HotelId
import com.dev.hotelmanagementservice.domain.Reservation
import com.dev.hotelmanagementservice.domain.ReservationId
import com.dev.hotelmanagementservice.domain.RoomId
import com.dev.hotelmanagementservice.domain.UserId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "reservations")
class ReservationEntity (
    @Id
    @Column(name = "ulid", length = 26)
    val ulid: String,

    @Column(name = "user_id", nullable = false, length = 26)
    val userId: String,

    @Column(name = "room_id", nullable = false, length = 26)
    val roomId: String,

    @Column(name = "hotel_id", nullable = false, length = 26)
    val hotelId: String,
) : BaseTimeEntity() {

    fun toDomain(): Reservation {
        return Reservation.reconstitute(
            id = ReservationId(ulid),
            userId = UserId(userId),
            roomId = RoomId(roomId),
            hotelId = HotelId(hotelId),
        )
    }

    companion object {
        fun from(reservation: Reservation): ReservationEntity {
            return ReservationEntity (
                ulid = reservation.id.id,
                userId = reservation.userId.id,
                roomId = reservation.roomId.value,
                hotelId = reservation.hotelId.id,
            )
        }
    }
}