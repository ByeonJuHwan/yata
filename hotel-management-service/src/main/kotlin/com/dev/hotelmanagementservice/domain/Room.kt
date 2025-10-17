package com.dev.hotelmanagementservice.domain

import com.github.f4b6a3.ulid.UlidCreator
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime

class Room private constructor(
    val id: RoomId,
    val hotelId: HotelId,
    roomName: RoomName,
    roomType: RoomType,
    capacity: Capacity,
    stock: Stock,
    basePrice: Money,
    bedType: BedType,
    status: RoomStatus,
    val createdAt: LocalDateTime?,
    updatedAt: LocalDateTime?,
) {
    var roomName: RoomName = roomName
        private set

    var roomType: RoomType = roomType
        private set

    var capacity: Capacity = capacity
        private set

    var stock: Stock = stock
        private set

    var basePrice: Money = basePrice
        private set

    var bedType: BedType = bedType
        private set

    var status: RoomStatus = status
        private set

    var updatedAt: LocalDateTime? = updatedAt
        private set

    companion object {
        fun create(
            hotelId: HotelId,
            roomName: String,
            roomType: String,
            capacity: Int,
            stock: Int,
            basePrice: BigDecimal,
            currency: String = "KRW",
            bedType: String,
        ): Room {
            return Room(
                id = RoomId(UlidCreator.getUlid().toString()),
                hotelId = hotelId,
                roomName = RoomName(roomName),
                roomType = RoomType.valueOf(roomType),
                capacity = Capacity(capacity),
                stock = Stock(stock),
                basePrice = Money(basePrice, currency),
                bedType = BedType.valueOf(bedType),
                status = RoomStatus.ACTIVE,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }


        // DB에서 복원할 때 사용 (Repository에서 호출)
        fun reconstitute(
            id: RoomId,
            hotelId: HotelId,
            roomName: RoomName,
            roomType: RoomType,
            capacity: Capacity,
            stock: Int,
            basePrice: Money,
            bedType: BedType,
            status: RoomStatus,
            createdAt: LocalDateTime?,
            updatedAt: LocalDateTime?,
        ): Room {
            return Room(
                id = id,
                hotelId = hotelId,
                roomName = roomName,
                roomType = roomType,
                capacity = capacity,
                stock = Stock(stock),
                basePrice = basePrice,
                bedType = bedType,
                status = status,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

}

data class Money(
    val amount: BigDecimal,
    val currency: String = "KRW"
) {
    init {
        require(amount >= BigDecimal.ZERO) { "Amount must be non-negative" }
        require(currency.length == 3) { "Currency must be 3-letter code" }
    }
}

@JvmInline
value class RoomId(val value: String) {
    init {
        require(value.isNotBlank()) { "방 ID 는 빈값일수 없습니다" }
    }
}

@JvmInline
value class RoomName(val value: String) {
    init {
        require(value.isNotBlank()) { "방 이름은 빈값일수 없습니다" }
        require(value.length <= 20) { "방 이름은 20자 이내로 입력해야 합니다." }
    }
}

@JvmInline
value class Stock(val stock: Int) {
    init {
        require(stock > 0) { "방개수는 1개 이상이어야 합니다." }
    }
}

@JvmInline
value class Capacity(val value: Int) {
    init {
        require(value > 0) { "수용인원은 0보다 커야합니다" }
        require(value <= 10) { "수용인원은 10보다 크거나작아야 합니다" }
    }
}

enum class RoomType {
    STANDARD,      // 스탠다드
    DELUXE,        // 디럭스
    SUITE,         // 스위트
    PREMIUM,       // 프리미엄
    EXECUTIVE,     // 이그제큐티브
    PENTHOUSE      // 펜트하우스
}

enum class BedType {
    SINGLE,        // 싱글 (1인)
    TWIN,          // 트윈 (싱글 2개)
    DOUBLE,        // 더블 (2인용 1개)
    QUEEN,         // 퀸 (2인용 큰 침대)
    KING,          // 킹 (2인용 가장 큰 침대)
    ONDOL          // 온돌 (한국 전통)
}

enum class RoomStatus {
    ACTIVE,        // 활성 (예약 가능)
    MAINTENANCE,   // 정비 중
    CLEANING,      // 청소 중
    OUT_OF_ORDER,  // 사용 불가
    INACTIVE       // 비활성
}