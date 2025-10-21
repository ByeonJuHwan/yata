package org.ktor_lecture.reservationservice.domain

import com.github.f4b6a3.ulid.UlidCreator

class Reservation private constructor(
    val id: ReservationId,
) {
    companion object {
        fun create(
            userId: String,
            roomId: String,
            hotelId: String,
        ) : Reservation {
            return Reservation(
                id = ReservationId(UlidCreator.getUlid().toString()),
            )
        }

        fun reconstitute(
            id: ReservationId,
        ): Reservation {
            return Reservation(
                id = id,
            )
        }
    }

}

@JvmInline
value class ReservationId(
    val id: String,
) {
    init {
        require(id.isNotBlank()) { "예약 ID 는 빈값일수 없습니다." }
    }
}
