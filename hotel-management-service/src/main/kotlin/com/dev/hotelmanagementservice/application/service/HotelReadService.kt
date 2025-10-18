package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchHotelDetailResponse
import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchMyHotelDetail
import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchMyHotelResponse
import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchRoomDetailResponse
import com.dev.hotelmanagementservice.application.port.`in`.hotel.SearchHotelDetailUseCase
import com.dev.hotelmanagementservice.application.port.`in`.hotel.SearchMyHotelsUseCase
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.application.service.excpetion.ErrorCode
import com.dev.hotelmanagementservice.application.service.excpetion.YataHotelException
import com.dev.hotelmanagementservice.domain.Room
import org.springframework.stereotype.Service

@Service
class HotelReadService (
    private val hotelRepository: HotelRepository,
    private val userRepository: UserRepository,
    private val roomRepository: RoomRepository,
) : SearchMyHotelsUseCase, SearchHotelDetailUseCase {

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

    // 고객용
    override fun searchHotelDetail(hotelId: String): SearchHotelDetailResponse {
        val hotel = hotelRepository.findById(hotelId).orElseThrow { throw YataHotelException(ErrorCode.HOTEL_NOT_FOUND) }
        val rooms: List<Room> = roomRepository.findByHotelId(hotelId)

        val roomDto: List<SearchRoomDetailResponse> = rooms
            .map {
                SearchRoomDetailResponse (
                    it.id.value,
                    it.roomName.value,
                    it.roomType.toString(),
                    it.capacity.value,
                    it.stock.stock,
                    it.basePrice.amount,
                    it.bedType.toString()
                )
            }

        return SearchHotelDetailResponse (
            hotelId = hotel.id.id,
            hotelName = hotel.name.name,
            roomResponses = roomDto,
        )
    }
}