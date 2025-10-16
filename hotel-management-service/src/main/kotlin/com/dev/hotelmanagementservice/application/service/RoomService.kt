package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest
import com.dev.hotelmanagementservice.application.port.`in`.room.RegisterRoomUseCase
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.domain.HotelId
import com.dev.hotelmanagementservice.domain.Room
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService (
    private val roomRepository: RoomRepository,
) : RegisterRoomUseCase {

    @Transactional
    override fun registerRoom(request: RegisterRoomRequest) {
        val room = Room.create(
            hotelId = HotelId(request.hotelId),
            roomName = request.roomName,
            roomType = request.roomType,
            capacity = request.capacity,
            basePrice = request.basePrice,
            bedType = request.bedType,
        )

        roomRepository.save(room)

    }
}