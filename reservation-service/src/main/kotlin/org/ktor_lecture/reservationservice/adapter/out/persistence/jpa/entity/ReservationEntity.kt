package org.ktor_lecture.reservationservice.adapter.out.persistence.jpa.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.ktor_lecture.reservationservice.domain.Reservation

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
    companion object {
        fun from(reservation: Reservation): ReservationEntity {
            return ReservationEntity(
                ulid = reservation.id.id,
                userId = reservation.userId.userId,
                roomId = reservation.roomId.roomId,
                hotelId = reservation.hotelId.hotelId,
            )
        }
    }

}