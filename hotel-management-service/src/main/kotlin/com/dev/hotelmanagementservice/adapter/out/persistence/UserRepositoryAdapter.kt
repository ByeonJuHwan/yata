package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.UserEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.UserJpaRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.User
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class UserRepositoryAdapter (
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findById(userId: String): Optional<User> {
        return userJpaRepository.findById(userId)
            .map { it.toDomain() }
    }

    override fun save(user: User) {
        val userEntity = UserEntity.from(user)
        userJpaRepository.save(userEntity)
    }
}