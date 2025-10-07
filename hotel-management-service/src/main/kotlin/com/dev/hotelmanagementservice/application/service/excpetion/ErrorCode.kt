package com.dev.hotelmanagementservice.application.service.excpetion

interface CodeInterFace {
    val code: Int
    val message: String
}

enum class ErrorCode(
    override val code: Int,
    override val message: String,
) : CodeInterFace {
}