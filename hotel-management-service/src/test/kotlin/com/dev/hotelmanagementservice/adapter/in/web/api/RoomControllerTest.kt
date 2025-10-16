package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.IntegrationTestBase
import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterRoomRequest
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.domain.BedType
import com.dev.hotelmanagementservice.domain.RoomType
import com.github.f4b6a3.ulid.UlidCreator
import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import java.math.BigDecimal
import kotlin.test.Test

class RoomControllerTest : IntegrationTestBase() {

    @Autowired
    private lateinit var roomRepository: RoomRepository

    @Test
    fun `방 정보를 받아서 저장한다`() {
        // given
        val request = RegisterRoomRequest(
            hotelId = UlidCreator.getUlid().toString(),
            roomName = "test room name",
            roomType = RoomType.STANDARD.toString(),
            capacity = 1,
            basePrice = BigDecimal.valueOf(100),
            bedType = BedType.KING.toString(),
        )

        // when
        mockMvc.post("/api/v1/rooms") {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(request)
        }.andExpect { status { is2xxSuccessful() } }

        // then
        Assertions.assertThat(roomRepository.findAll()).hasSize(1)
    }
}