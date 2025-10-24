package com.dev.hotelmanagementservice.domain.id

data class HotelId(
    val hotelId: String,
) {
    init {
        require(hotelId.isNotBlank()) { throw IllegalArgumentException("호텔 ID 는 빈 값일수 없습니다") }
    }
}

data class OwnerId(
    val ownerId: String,
) {
    init {
        require(ownerId.isNotBlank()) { throw IllegalArgumentException("소유자 ID 는 빈 값일수 없습니다") }
    }
}

data class RoomId(val roomId: String) {
    init {
        require(roomId.isNotBlank()) { "방 ID 는 빈값일수 없습니다" }
    }
}


data class HotelOptionId(val hotelOptionId: String) {
    init {
        require(hotelOptionId.isNotBlank()) { "호텔 옵션 ID 는 빈값일수 없습니다" }
    }
}

data class RoomInventoryId(val roomInventoryId: String) {
    init {
        require(roomInventoryId.isNotBlank()) {"방 재고 ID 는 빈값일수 없습니다."}
    }
}

