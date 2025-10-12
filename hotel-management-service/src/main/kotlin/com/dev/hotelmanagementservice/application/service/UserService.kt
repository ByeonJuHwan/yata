package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.consumer.message.UserCreateMessage
import com.dev.hotelmanagementservice.application.port.`in`.UserCreateUseCase
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
) : UserCreateUseCase{

    @Transactional
    override fun createUser(message: UserCreateMessage) {
        val user = User.create(
            message.userId,
            message.username,
        )

        userRepository.save(user)
    }
}