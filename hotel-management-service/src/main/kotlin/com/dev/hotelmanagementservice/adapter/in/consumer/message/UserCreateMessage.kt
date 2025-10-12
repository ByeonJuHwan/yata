package com.dev.hotelmanagementservice.adapter.`in`.consumer.message

import kotlinx.serialization.Serializable

@Serializable
data class UserCreateMessage (
    val userId: String,
    val username: String,
)