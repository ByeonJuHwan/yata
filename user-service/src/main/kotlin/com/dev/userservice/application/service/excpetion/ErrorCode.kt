package com.dev.userservice.application.service.excpetion

interface CodeInterFace {
    val code: Int
    val message: String
}

enum class ErrorCode(
    override val code: Int,
    override val message: String,
) : CodeInterFace {
    AUTH_CONFIG_NOT_FOUND(400, "auth config not found"),
    FAILED_TO_CALL_CLIENT(400, "Failed to call client"),
    CALL_RESULT_BODY_NULL(400, "body is null"),
    PROVIDER_NOT_FOUND(404, "provider not found"),
    TOKEN_IS_INVALID(500, "token invalid"),
    TOKEN_IS_EXPIRED(500, "token expired"),
}