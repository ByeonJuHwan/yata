package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.User
import java.util.Optional

interface UserRepository {
    fun findById(userId: String) : Optional<User>
}