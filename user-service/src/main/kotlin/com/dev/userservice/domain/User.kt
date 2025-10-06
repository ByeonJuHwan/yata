package com.dev.userservice.domain

import java.time.LocalDateTime

class User (
    val ulid: String,
    val username: String,
    val email: String,
    var accessToken: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
}