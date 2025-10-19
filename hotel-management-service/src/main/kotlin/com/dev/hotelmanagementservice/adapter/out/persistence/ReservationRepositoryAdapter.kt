package com.dev.hotelmanagementservice.adapter.out.persistence

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.ReservationEntity
import com.dev.hotelmanagementservice.adapter.out.persistence.jpa.ReservationJpaRepository
import com.dev.hotelmanagementservice.application.port.out.ReservationRepository
import com.dev.hotelmanagementservice.domain.Reservation
import org.springframework.stereotype.Component

@Component
class ReservationRepositoryAdapter (
    private val reservationJpaRepository: ReservationJpaRepository,
) : ReservationRepository {

    override fun save(reservation: Reservation) {
        val reservationEntity = ReservationEntity.from(reservation)
        reservationJpaRepository.save(reservationEntity)
    }

    override fun findAll(): List<Reservation> {
        return reservationJpaRepository.findAll()
            .map { it.toDomain() }
    }

    override fun deleteAll() {
        reservationJpaRepository.deleteAll()
    }
}