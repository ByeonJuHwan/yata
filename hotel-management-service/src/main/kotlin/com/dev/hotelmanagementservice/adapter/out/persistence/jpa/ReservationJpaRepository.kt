package com.dev.hotelmanagementservice.adapter.out.persistence.jpa

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReservationJpaRepository : JpaRepository<ReservationEntity, String> {
}