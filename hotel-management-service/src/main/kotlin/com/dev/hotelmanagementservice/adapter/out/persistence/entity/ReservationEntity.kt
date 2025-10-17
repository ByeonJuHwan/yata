package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.Reservation
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class ReservationEntity (
    @Id
    @Column(name = "ulid", length = 26)
    val ulid: String,

    @Column(name = "user_id", nullable = false, length = 26)
    val userId: String,

    @Column(name = "room_id", nullable = false, length = 26)
    val roomId: String,

    @Column(name = "room_id", nullable = false, length = 26)
    val hotelId: String,
) : BaseTimeEntity() {

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