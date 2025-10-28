package org.ktor_lecture.reservationservice.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(YataHotelException::class)
    fun handleYataHotelException(ex: YataHotelException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = ex.code,
            message = ex.message ?: "서버 오류 입니다"
        )

        return ResponseEntity
            .status(HttpStatus.valueOf(ex.code))
            .body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            code = 500,
            message = "서버 내부 오류가 발생했습니다."
        )

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorResponse)
    }
}

data class ErrorResponse(
    val code: Int,
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now()
)