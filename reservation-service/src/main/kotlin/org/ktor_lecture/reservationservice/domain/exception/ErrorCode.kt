package org.ktor_lecture.reservationservice.domain.exception

interface CodeInterFace {
    val code: Int
    val message: String
}

enum class ErrorCode(
    override val code: Int,
    override val message: String,
) : CodeInterFace {
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다.") ,
    RESERVATION_FAILED(500, "예약에 실패했습니다.")
}