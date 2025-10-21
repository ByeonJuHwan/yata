package org.ktor_lecture.reservationservice.application.port.out

import org.ktor_lecture.reservationservice.domain.Reservation

interface ReservationRepository {
    fun save(reservation: Reservation)
    fun findAll(): List<Reservation>
    fun deleteAll()
}