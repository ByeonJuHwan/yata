package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.IntegrationTestBase
import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.User
import com.dev.hotelmanagementservice.domain.UserId
import com.dev.hotelmanagementservice.domain.UserName
import com.github.f4b6a3.ulid.UlidCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post

class HotelControllerTest : IntegrationTestBase() {

    @Autowired
    private lateinit var hotelRepository: HotelRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    private val userId = UlidCreator.getUlid().toString()

    @Test
    fun `존재하지 않는 사용자로 호텔 등록 시 실패한다`() {
        // given
        val request = RegisterHotelRequest(
             "테스트 호텔",
             null,
             "대한민국",
             "서울 특별시",
             "강남구",
             null,
             "01073225858",
             null
         )

        // when
        mockMvc.post("/api/v1/hotels") {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(request)
            header("X-USER-ID", userId)
        }.andExpect {
            status {isNotFound()}
            jsonPath("$.message") {value("사용자를 찾을 수 없습니다.")}
        }

        // then
        assertThat(hotelRepository.findAll()).isEmpty()
    }

    @Test
    fun `호텔 등록에 성공한다`() {
        // given
        val request = RegisterHotelRequest(
             "테스트 호텔",
             null,
             "대한민국",
             "서울 특별시",
             "강남구",
             null,
             "01073225858",
             null
         )

        val user = User(
            id = UserId(userId),
            username = UserName("테스트"),
        )

        userRepository.save(user)

        // when
        mockMvc.post("/api/v1/hotels") {
            contentType = MediaType.APPLICATION_JSON
            content = toJson(request)
            header("X-USER-ID", userId)
        }.andExpect {
            status {isCreated()}
        }

        // then
        assertThat(hotelRepository.findAll()).size().isEqualTo(1)
    }
}
