package org.ktor_lecture.reservationservice.domain.id

data class ReservationId(val id: String) {
    init {
        require(id.isNotBlank()) { "예약 ID는 빈 값일 수 없습니다." }
    }
}

data class HotelId(val hotelId: String) {
    init {
        require(hotelId.isNotBlank()) {"호텔 ID는 빈 값일 수 없습니다."}
    }
}

data class UserId(val userId: String) {
    init {
        require(userId.isNotBlank()) {"유저 ID는 빈 값일 수 없습니다."}
    }
}

data class RoomId(val roomId: String) {
    init {
        require(roomId.isNotBlank()) {"방 ID는 빈 값일 수 없습니다."}
    }
}
