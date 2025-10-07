package com.dev.hotelmanagementservice.domain

import com.dev.hotelmanagementservice.domain.status.HotelStatus

class Hotel (
    val id: HotelId,
    private val ownerId: OwnerId,
    private var name: HotelName,
    private var description: Description?,
    private var address: Address,
    private var phoneNumber: PhoneNumber,
    private var email: Email?,
    private var status: HotelStatus,
) {
}

data class HotelId(
    val id: String,
) {
    init {
        require(id.isNotBlank()) { throw IllegalArgumentException("호텔 ID 는 빈 값일수 없습니다") }
    }
}

data class OwnerId(
    val ownerId: String,
) {
    init {
        require(ownerId.isNotBlank()) { throw IllegalArgumentException("소유자 ID 는 빈 값일수 없습니다") }
    }
}

data class HotelName(
    val name: String,
) {
    init {
        require(name.isNotBlank()) { throw IllegalArgumentException("호텔 이름은 빈 값일수 없습니다") }
        require(name.length in 1..200) { throw IllegalArgumentException("호텔 이름은 1~200자 이내로 입력해주세요") }
    }
}

data class Description(
    val description: String,
) {
    init {
        require(description.length <= 1000) { throw IllegalArgumentException("호텔 설명은 1000자 이내로 입력해주세요") }
    }
}

data class Address(
    val country: String,
    val city: String,
    val street: String,
    val zipCode: String?
) {
    init {
        require(country.isNotBlank()) { throw IllegalArgumentException("국가는 빈 값일수 없습니다") }
        require(city.isNotBlank()) { throw IllegalArgumentException("도시는 빈 값일수 없습니다") }
        require(street.isNotBlank()) { throw IllegalArgumentException("상세 주소는 빈 값일수 없습니다") }
        zipCode?.let {
            require(it.length <= 20) { throw IllegalArgumentException("우편번호는 20자 이내로 입력해주세요") }
        }
    }
}

data class PhoneNumber(
    val phoneNumber: String,
) {
    init {
        require(phoneNumber.isNotBlank()) { throw IllegalArgumentException("전화번호는 빈 값일수 없습니다") }
        require(phoneNumber.length <= 20) { throw IllegalArgumentException("전화번호는 20자 이내로 입력해주세요") }
    }
}

data class Email(
    val email: String,
) {
    init {
        require(email.isNotBlank()) { throw IllegalArgumentException("이메일은 빈 값일수 없습니다") }
        require(email.length <= 100) { throw IllegalArgumentException("이메일은 100자 이내로 입력해주세요") }
        require(EMAIL_REGEX.matches(email)) { throw IllegalArgumentException("이메일 형식이 올바르지 않습니다") }
    }

    companion object {
        private val EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    }
}
