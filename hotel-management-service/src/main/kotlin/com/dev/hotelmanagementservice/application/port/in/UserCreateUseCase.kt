package com.dev.hotelmanagementservice.application.port.`in`

import com.dev.hotelmanagementservice.adapter.`in`.consumer.message.UserCreateMessage

interface UserCreateUseCase {
    fun createUser(message: UserCreateMessage)
}