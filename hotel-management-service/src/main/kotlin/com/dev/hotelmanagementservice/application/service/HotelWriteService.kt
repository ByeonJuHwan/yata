package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.`in`.hotel.RegisterHotelUseCase
import com.dev.hotelmanagementservice.application.port.out.EventPublisher
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.exception.ErrorCode
import com.dev.hotelmanagementservice.domain.exception.YataHotelException
import com.dev.hotelmanagementservice.domain.Hotel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelWriteService (
    private val hotelRepository: HotelRepository,
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher,
) : RegisterHotelUseCase {

    @Transactional
    override fun registerHotel(userId: String, request: RegisterHotelRequest) {
        userRepository.findById(userId).orElseThrow { throw YataHotelException(ErrorCode.USER_NOT_FOUND) }

        val hotel = Hotel.create(
            userId,
            request.name,
            request.description,
            request.country,
            request.city,
            request.street,
            request.zipCode,
            request.phoneNumber,
            request.email,
        )

        hotelRepository.saveHotel(hotel)

        // TODO 사용자 서비스의 DB 로 마이그 및 ElasticSearch 로 전송
    }
}