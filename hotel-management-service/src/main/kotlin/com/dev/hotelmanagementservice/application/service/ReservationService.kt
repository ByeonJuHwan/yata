package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.application.port.`in`.reservation.CreateReservationUseCase
import com.dev.hotelmanagementservice.application.port.out.ReservationRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.application.service.excpetion.ErrorCode
import com.dev.hotelmanagementservice.application.service.excpetion.YataHotelException
import com.dev.hotelmanagementservice.domain.Reservation
import com.dev.hotelmanagementservice.domain.Room
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService (
    private val roomRepository: RoomRepository,
    private val reservationRepository: ReservationRepository,
) : CreateReservationUseCase {

    @Transactional
    override fun createReservation(userId: String, request: CreateReservationRequest) {
        // 현재 재고가 남아있는지 확인 -> PESSIMISTIC_WRITE
        val room: Room = roomRepository.getRoomStockWithLock(request.roomId) ?: throw YataHotelException(ErrorCode.ROOM_NOT_FOUND)

        // 재고가 남아있는지 확인
        if (!room.stock.isAvailable()) {
            throw YataHotelException(ErrorCode.ROOM_STOCK_NOT_AVAILABLE)
        }

        val deductedRoom = room.deductStock(room)
        roomRepository.deductStock(deductedRoom)

        // 예약생성
        val reservation = Reservation.create(
            userId = userId,
            roomId = request.roomId,
            hotelId = request.hotelId,
        )

        reservationRepository.save(reservation)
    }
}