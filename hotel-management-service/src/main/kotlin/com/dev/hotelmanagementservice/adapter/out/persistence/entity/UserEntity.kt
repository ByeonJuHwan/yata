package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.User
import com.dev.hotelmanagementservice.domain.UserId
import com.dev.hotelmanagementservice.domain.UserName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class UserEntity (
    @Id
    @Column(name = "ulid", length = 26)
    val ulid: String,

    @Column(name = "username", nullable = false, unique = true, length = 50)
    val username: String,
) {
    fun toDomain(): User {
        return User (
            id = UserId(ulid),
            username = UserName(username),
        )
    }

    companion object {
        fun from(user: User) : UserEntity {
            return UserEntity(
                ulid = user.id.id,
                username = user.username.username
            )
        }
    }
}