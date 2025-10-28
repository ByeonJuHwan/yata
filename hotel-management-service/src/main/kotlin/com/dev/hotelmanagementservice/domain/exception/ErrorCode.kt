package com.dev.hotelmanagementservice.domain.exception

interface CodeInterFace {
    val code: Int
    val message: String
}

enum class ErrorCode(
    override val code: Int,
    override val message: String,
) : CodeInterFace {
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다.") ,
    HOTEL_NOT_FOUND(404,"호텔을 찾을 수 없습니다."),
    ROOM_NOT_FOUND(404,"방 정보를 찾을 수 없습니다."),
    ROOM_STOCK_NOT_AVAILABLE(400, "방 재고가 부족합니다.")
}