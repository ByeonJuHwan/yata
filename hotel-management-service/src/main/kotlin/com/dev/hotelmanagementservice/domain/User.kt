package com.dev.hotelmanagementservice.domain

class User (
    val id: UserId,
    val username: UserName,
) {
    companion object {
        fun create(
            id: String,
            userName: String,
        ): User {
            return User(
                id = UserId(id),
                username = UserName(userName),
            )
        }
    }
}

@JvmInline
value class UserId(
    val id: String,
) {

    init {
        require(id.isNotBlank()) { throw IllegalArgumentException("User ID 는 빈 값일수 없습니다") }
    }
}

@JvmInline
value class UserName(
    val username: String,
) {
    init {
        require(username.isNotBlank()) { throw IllegalArgumentException("이름은 빈 값일수 없습니다") }
        require(username.length in 1..50) { throw IllegalArgumentException("이름은 1~50자 이내로 입력해주세요") }
    }
}