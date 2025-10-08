package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.`in`.RegisterHotelUseCase
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.application.service.excpetion.ErrorCode
import com.dev.hotelmanagementservice.application.service.excpetion.YataHotelException
import com.dev.hotelmanagementservice.domain.Hotel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class HotelWriteService (
    private val hotelRepository: HotelRepository,
    private val userRepository: UserRepository,
) : RegisterHotelUseCase {

    @Transactional
    override fun registerHotel(userId: String, request: RegisterHotelRequest) {
        userRepository.findById(userId).orElseThrow { throw YataHotelException(ErrorCode.USER_NOT_FOUND) }

        val hotel = Hotel.register(
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
    }
}