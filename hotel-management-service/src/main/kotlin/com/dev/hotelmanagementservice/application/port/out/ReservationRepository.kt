package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.Reservation

interface ReservationRepository {
    fun save(reservation: Reservation)
}