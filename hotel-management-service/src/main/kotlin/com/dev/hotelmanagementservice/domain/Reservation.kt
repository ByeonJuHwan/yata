package com.dev.hotelmanagementservice.domain

import com.github.f4b6a3.ulid.UlidCreator

class Reservation private constructor(
    val id: ReservationId,
    val userId: UserId,
    val roomId: RoomId,
    val hotelId: HotelId,
) {
    companion object {
        fun create(
            userId: String,
            roomId: String,
            hotelId: String,
        ) : Reservation {
            return Reservation(
                id = ReservationId(UlidCreator.getUlid().toString()),
                userId = UserId(userId),
                roomId = RoomId(roomId),
                hotelId = HotelId(hotelId),
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
