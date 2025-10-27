package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest
import com.dev.hotelmanagementservice.application.port.out.RoomInventoryRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.domain.RoomInventory
import com.dev.hotelmanagementservice.domain.status.BedType
import com.dev.hotelmanagementservice.domain.status.RoomType
import com.github.f4b6a3.ulid.UlidCreator
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class RoomServiceTest {

    @InjectMockKs
    private lateinit var roomService: RoomService

    @MockK
    private lateinit var roomRepository: RoomRepository

    @MockK
    private lateinit var roomInventoryRepository: RoomInventoryRepository


    @Test
    fun `방 정보를 받아서 저장합니다`() {
        // given
        val request = RegisterRoomRequest(
            hotelId = UlidCreator.getUlid().toString(),
            roomName = "test room name",
            roomType = RoomType.STANDARD.toString(),
            capacity = 1,
            totalRoom = 1,
            basePrice = BigDecimal.valueOf(100),
            bedType = BedType.KING.toString(),
        )

        every { roomRepository.save(any()) } answers {
            firstArg()
        }

        every { roomInventoryRepository.saveAll(any()) } answers {
            firstArg() as List<RoomInventory>
        }

        // when
        roomService.registerRoom(request)

        // then
        verify(exactly = 1) {roomRepository.save(any())}
        verify(exactly = 1) {roomInventoryRepository.saveAll(any())}
    }
}