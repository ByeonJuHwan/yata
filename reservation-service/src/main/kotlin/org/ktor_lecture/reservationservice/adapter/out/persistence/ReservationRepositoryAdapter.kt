package org.ktor_lecture.reservationservice.adapter.out.persistence

import org.ktor_lecture.reservationservice.adapter.out.persistence.jpa.ReservationJpaRepository
import org.ktor_lecture.reservationservice.application.port.out.ReservationRepository
import org.ktor_lecture.reservationservice.domain.Reservation
import org.springframework.stereotype.Component

@Component
class ReservationRepositoryAdapter (
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationRepository {
    override fun save(reservation: Reservation) {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Reservation> {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

}