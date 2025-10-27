package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest
import com.dev.hotelmanagementservice.adapter.`in`.web.response.DeductRoomInventoryResponse
import com.dev.hotelmanagementservice.application.port.`in`.room.DeductRoomInventoryUseCase
import com.dev.hotelmanagementservice.application.port.`in`.room.IncreaseRoomInventoryUseCase
import com.dev.hotelmanagementservice.application.port.`in`.room.RegisterRoomUseCase
import com.dev.hotelmanagementservice.application.port.out.RoomInventoryRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.application.service.excpetion.ErrorCode
import com.dev.hotelmanagementservice.application.service.excpetion.YataHotelException
import com.dev.hotelmanagementservice.domain.AvailableCount
import com.dev.hotelmanagementservice.domain.Room
import com.dev.hotelmanagementservice.domain.RoomInventory
import com.dev.hotelmanagementservice.domain.id.HotelId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoomService (
    private val roomRepository: RoomRepository,
    private val roomInventoryRepository: RoomInventoryRepository,
) : RegisterRoomUseCase, DeductRoomInventoryUseCase, IncreaseRoomInventoryUseCase {

    /**
     * 방에 대한 정보를 입력하면 30일까지 방 기본 정보 생성
     */
    @Transactional
    override fun registerRoom(request: RegisterRoomRequest) {
        val room = Room.create(
            hotelId = HotelId(request.hotelId),
            roomName = request.roomName,
            roomType = request.roomType,
            capacity = request.capacity,
            totalRoom = request.totalRoom,
            basePrice = request.basePrice,
        )

        roomRepository.save(room)

        val roomInventories = RoomInventory.createFor30Days(
            roomId = room.id,
            price = room.basePrice,
            availableCount = AvailableCount(room.totalRoom.totalRoom),
        )

        roomInventoryRepository.saveAll(roomInventories)
    }

    @Transactional
    override fun deductRoomInventory(request: CreateReservationRequest): DeductRoomInventoryResponse {
        // 락 걸어서 가져오기 재고 없으면 예외
        val roomInventory = roomInventoryRepository.findByIdAndDateWithLock(request.roomId, request.date) ?: throw YataHotelException(ErrorCode.ROOM_NOT_FOUND)

        roomInventory.deduct()
        roomInventoryRepository.updateAvailableCount(roomInventory)

        return DeductRoomInventoryResponse(roomInventory.id.roomInventoryId)
    }

    @Transactional
    override fun increaseRoomInventory(roomInventoryId: String) {
        val roomInventory = roomInventoryRepository
            .findById(roomInventoryId)
            .orElseThrow { throw YataHotelException(ErrorCode.ROOM_NOT_FOUND)  }

        roomInventory.increase()
        roomInventoryRepository.updateAvailableCount(roomInventory)
    }
}