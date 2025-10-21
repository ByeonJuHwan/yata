package org.ktor_lecture.reservationservice.adapter.out.persistence.jpa

import org.ktor_lecture.reservationservice.adapter.out.persistence.jpa.entity.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationJpaRepository : JpaRepository<ReservationEntity, String> {
}