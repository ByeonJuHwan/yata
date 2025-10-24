package com.dev.hotelmanagementservice.domain

import com.dev.hotelmanagementservice.domain.id.HotelId
import com.dev.hotelmanagementservice.domain.id.OwnerId
import com.dev.hotelmanagementservice.domain.status.HotelStatus
import com.github.f4b6a3.ulid.UlidCreator

class Hotel (
    val id: HotelId,
    val ownerId: OwnerId,
    var name: HotelName,
    var description: String?,
    var address: Address,
    var phoneNumber: PhoneNumber,
    var email: Email,
    var status: HotelStatus,
) {
    companion object {
        fun create(
            ownerId: String,
            hotelName: String,
            description: String?,
            country: String,
            city: String,
            street: String,
            zipCode: String,
            phoneNumber: String,
            email: String,
        ) : Hotel {
            return Hotel(
                id = HotelId(UlidCreator.getUlid().toString()),
                ownerId = OwnerId(ownerId),
                name = HotelName(hotelName),
                description = description,
                address = Address(country, city, street, zipCode),
                phoneNumber = PhoneNumber(phoneNumber),
                email = Email(email),
                status = HotelStatus.ACTIVE,
            )
        }
    }
}

data class HotelName(
    val hotelName: String,
) {
    init {
        require(hotelName.isNotBlank()) { throw IllegalArgumentException("호텔 이름은 빈 값일수 없습니다") }
        require(hotelName.length in 1..200) { throw IllegalArgumentException("호텔 이름은 1~200자 이내로 입력해주세요") }
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
