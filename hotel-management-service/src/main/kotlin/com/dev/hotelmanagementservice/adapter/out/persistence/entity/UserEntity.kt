package com.dev.hotelmanagementservice.adapter.out.persistence.entity

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
}