package com.dev.hotelmanagementservice.adapter.out.persistence.jpa

import com.dev.hotelmanagementservice.adapter.out.persistence.entity.RoomEntity
import com.dev.hotelmanagementservice.domain.Room
import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.data.repository.query.Param
import java.util.Optional

interface RoomJpaRepository : JpaRepository<RoomEntity, String> {
    fun findByHotelId(hotelId: String): List<RoomEntity>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RoomEntity r WHERE r.id = :roomId")
    fun findByIdWithLock(@Param("roomId") roomId: String): RoomEntity?

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE RoomEntity r SET r.stock = :stock WHERE r.id = :roomId")
    fun updateStock(
        @Param("roomId") roomId: String,
        @Param("stock") stock: Int
    )

}