package com.dev.hotelmanagementservice.adapter.out

import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.User
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class UserRepositoryAdapter (

) : UserRepository {
    override fun findById(userId: String): Optional<User> {
        return Optional.empty()
    }
}