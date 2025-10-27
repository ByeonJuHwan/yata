package com.dev.hotelmanagementservice.adapter.out.persistence.jpa

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomInventoryEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface RoomInventoryJpaRepository : JpaRepository<RoomInventoryEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT ri FROM RoomInventoryEntity ri WHERE ri.roomId = :roomId AND ri.date = :date")
    fun findByIdAndDateWithLock(roomId: String, date: LocalDate): RoomInventoryEntity?

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE RoomInventoryEntity ri SET ri.availableCount = :#{#entity.availableCount} WHERE ri.roomInventoryId = :#{#entity.roomInventoryId}")
    fun updateAvailableCount(@Param("entity") entity: RoomInventoryEntity)
}