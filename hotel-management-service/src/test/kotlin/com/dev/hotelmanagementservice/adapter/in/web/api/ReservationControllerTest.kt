package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.IntegrationTestBase
import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.application.port.out.ReservationRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.domain.Room
import com.github.f4b6a3.ulid.UlidCreator
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.math.BigDecimal
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.test.Test

class ReservationControllerTest : IntegrationTestBase() {

    @Autowired
    private lateinit var roomRepository: RoomRepository

    @Autowired
    private lateinit var reservationRepository: ReservationRepository


    private val userId = UlidCreator.getUlid().toString()
    private val hotelId = "test-hotel-id"

    @AfterEach
    fun tearDown() {
        roomRepository.deleteAll()
        reservationRepository.deleteAll()
    }

//    @Test
//    fun `호텔 예약 성공 테스트`() {
//        // given
//        val room = Room.create(
//            hotelId = TODO(),
//            roomName = TODO(),
//            roomType = TODO(),
//            capacity = TODO(),
//            totalRoom = TODO(),
//            basePrice = TODO(),
//            currency = TODO()
//        )
//
//        val savedRoom = roomRepository.save(room)
//        val roomId = savedRoom.id.value
//
//        val result = CreateReservationRequest(
//            roomId = roomId,
//            hotelId = hotelId,
//        )
//
//        // when
//        mockMvc.perform(
//            MockMvcRequestBuilders.post("/api/v1/reservation")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJson(result))
//                .header("X-USER-ID", userId)
//        )
//
//        // then
//        assertThat (reservationRepository.findAll().size).isEqualTo(1)
//    }
//
//    @Test
//    fun `재고 1개_동시 10명 예약_1명만 성공`() {
//        val room = Room.create(
//            hotelId = HotelId(hotelId),
//            roomName = "Deluxe Room",
//            roomType = RoomType.STANDARD.toString(),
//            capacity = 2,
//            stock = 1,
//            basePrice = BigDecimal("100000"),
//            bedType = BedType.KING.toString(),
//        )
//
//        val savedRoom = roomRepository.save(room)
//        val roomId = savedRoom.id.value
//
//        // given
//        val request = CreateReservationRequest(
//            roomId = roomId,
//            hotelId = hotelId,
//        )
//
//        // when: 100개 동시 요청
//        val threadCount = 10
//        val executorService = Executors.newFixedThreadPool(threadCount)
//        val latch = CountDownLatch(threadCount)
//
//        repeat(threadCount) { index ->
//            executorService.submit {
//                try {
//                    mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/reservation")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(toJson(request))
//                            .header("X-USER-ID", userId)
//                    )
//                } catch (e: Exception) {
//                    println("예약 실패 $index, ${e.message}")
//                } finally {
//                    latch.countDown()
//                }
//            }
//        }
//
//        latch.await(10, TimeUnit.SECONDS)
//        executorService.shutdown()
//
//        // then
//        assertThat (roomRepository.findByHotelId(hotelId)).hasSize(1)
//        assertThat(roomRepository.findById(roomId).get().stock.stock).isEqualTo(0)
//        assertThat(reservationRepository.findAll().size).isEqualTo(1)
//    }
//
//    @Test
//    fun `재고 1개에 동시에 2개 요청온경우`() {
//        // given
//        val room = Room.create(
//            hotelId = HotelId(hotelId),
//            roomName = "Deluxe Room",
//            roomType = RoomType.STANDARD.toString(),
//            capacity = 2,
//            stock = 1,
//            basePrice = BigDecimal("100000"),
//            bedType = BedType.KING.toString(),
//        )
//
//        val savedRoom = roomRepository.save(room)
//        val roomId = savedRoom.id.value
//
//        val request1 = CreateReservationRequest(
//            roomId = roomId,
//            hotelId = hotelId,
//        )
//
//        val request2 = CreateReservationRequest(
//            roomId = roomId,
//            hotelId = hotelId,
//        )
//
//
//        // when
//        CompletableFuture.allOf(
//            CompletableFuture.runAsync {
//                try {
//                    mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/reservation")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(toJson(request1))
//                            .header("X-USER-ID", userId)
//                    )
//                } catch (e: Exception) {
//                    print(e.message)
//                }
//            },
//            CompletableFuture.runAsync {
//                try {
//                    mockMvc.perform(
//                        MockMvcRequestBuilders.post("/api/v1/reservation")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(toJson(request2))
//                            .header("X-USER-ID", userId)
//                    )
//                } catch (e: Exception) {
//                    println(e.message)
//                }
//            }
//        ).join()
//
//        // then
//        assertThat(reservationRepository.findAll().size).isEqualTo(1)
//        assertThat(roomRepository.findById(roomId).get().stock.stock).isEqualTo(0)
//    }

}