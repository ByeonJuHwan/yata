package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.application.port.out.ReservationRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.application.service.excpetion.ErrorCode
import com.dev.hotelmanagementservice.application.service.excpetion.YataHotelException
import com.dev.hotelmanagementservice.domain.*
import com.github.f4b6a3.ulid.UlidCreator
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ReservationServiceTest {

    @MockK
    private lateinit var reservationRepository: ReservationRepository

    @MockK
    private lateinit var roomRepository: RoomRepository

    @InjectMockKs
    private lateinit var reservationService: ReservationService

    private val userId = UlidCreator.getUlid().toString()

    @Test
    fun `예약을 생성한다`() {
        // given
        val roomId = "test-room-id"
        val hotelId = "test-hotel-id"

        val request = CreateReservationRequest (
            roomId = roomId,
            hotelId = hotelId,
        )

        val room = Room.create(
            hotelId = HotelId(hotelId),
            roomName = "test room name",
            roomType = RoomType.STANDARD.toString(),
            capacity = 1,
            stock = 1,
            basePrice = BigDecimal.ZERO,
            bedType = BedType.KING.toString(),
        )

        every {roomRepository.getRoomStockWithLock(roomId)}  returns room
        every { roomRepository.deductStock(any()) } just runs
        every { reservationRepository.save(any()) } just runs

        // when
        reservationService.createReservation(userId, request)

        // then
        verify (exactly = 1) {reservationRepository.save(any<Reservation>())}
    }

    @Test
    fun `재고가 없으면 예외를 발생시킨다`() {
        // given
        val roomId = "test-room-id"
        val hotelId = "test-hotel-id"

        val request = CreateReservationRequest (
            roomId = roomId,
            hotelId = hotelId,
        )

        val room = Room.create(
            hotelId = HotelId(hotelId),
            roomName = "test room name",
            roomType = RoomType.STANDARD.toString(),
            capacity = 1,
            stock = 0,
            basePrice = BigDecimal.ZERO,
            bedType = BedType.KING.toString(),
        )

        every {roomRepository.getRoomStockWithLock(roomId)}  returns room

        // when&then
        val exception = assertThrows<YataHotelException> {
            reservationService.createReservation(userId, request)
        }

        assertThat(exception.codeInterface.message).isEqualTo(ErrorCode.ROOM_STOCK_NOT_AVAILABLE.message)
        verify(exactly = 1) {roomRepository.getRoomStockWithLock(roomId) }
    }


}