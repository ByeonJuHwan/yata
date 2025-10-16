package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchMyHotelDetail
import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchMyHotelResponse
import com.dev.hotelmanagementservice.application.port.`in`.hotel.SearchMyHotelsUseCase
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.application.service.excpetion.ErrorCode
import com.dev.hotelmanagementservice.application.service.excpetion.YataHotelException
import org.springframework.stereotype.Service

@Service
class HotelReadService (
    private val hotelRepository: HotelRepository,
    private val userRepository: UserRepository,
) : SearchMyHotelsUseCase {

    override fun getMyHotels(userId: String): SearchMyHotelResponse {
        val user = userRepository.findById(userId).orElseThrow { throw YataHotelException(ErrorCode.USER_NOT_FOUND) }
        val hotels = hotelRepository.findByOwnerId(userId)

        return SearchMyHotelResponse(
            user.id.id,
            user.username.username,
            hotels
                .map {
                    SearchMyHotelDetail(
                        it.id.id,
                        it.name.name
                    )
                }
        )
    }
}