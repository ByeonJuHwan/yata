package com.dev.userservice.adapter.out.persistance.entity

import com.dev.userservice.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @Column(name = "ulid", length = 26)
    val ulid: String,

    @Column(name = "username", nullable = false, unique = true, length = 50)
    val username: String,

    @Column(name = "email", nullable = false, unique = true, length = 100)
    val email: String,

    @Column(name = "access_token", length = 255)
    val accessToken: String? = null,

) : BaseTimeEntity() {
}

fun toDomain(entity: UserEntity) = User(
    ulid = entity.ulid,
    username = entity.username,
    email = entity.email,
    accessToken = entity.accessToken,
    createdAt = entity.createdAt!!,
    updatedAt = entity.updatedAt!!
)