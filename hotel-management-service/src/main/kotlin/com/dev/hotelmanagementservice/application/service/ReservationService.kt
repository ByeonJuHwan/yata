package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.application.port.`in`.reservation.CreateReservationUseCase
import com.dev.hotelmanagementservice.application.port.out.ReservationRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReservationService (
    private val roomRepository: RoomRepository,
    private val reservationRepository: ReservationRepository,
) : CreateReservationUseCase {

    @Transactional
    override fun createReservation(userId: String, request: CreateReservationRequest) {
        // 결제

    }
}