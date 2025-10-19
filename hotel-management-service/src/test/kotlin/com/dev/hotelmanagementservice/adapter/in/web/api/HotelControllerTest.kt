package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.IntegrationTestBase
import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.Address
import com.dev.hotelmanagementservice.domain.Hotel
import com.dev.hotelmanagementservice.domain.HotelId
import com.dev.hotelmanagementservice.domain.HotelName
import com.dev.hotelmanagementservice.domain.OwnerId
import com.dev.hotelmanagementservice.domain.PhoneNumber
import com.dev.hotelmanagementservice.domain.User
import com.dev.hotelmanagementservice.domain.UserId
import com.dev.hotelmanagementservice.domain.UserName
import com.dev.hotelmanagementservice.domain.status.HotelStatus
import com.github.f4b6a3.ulid.UlidCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional

@Transactional
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


    @Test
    fun `내가 등록한 호텔 목록을 조회한다`() {
        // given
        val hotel = Hotel(
            id = HotelId(UlidCreator.getUlid().toString()),
            ownerId = OwnerId(userId),
            name = HotelName("테스트 호텔"),
            description = null,
            address = Address(
                country = "대한민국",
                city = "대한민국",
                street = "강남구",
                zipCode = null,
            ),
            phoneNumber = PhoneNumber("01073225858"),
            email = null,
            status = HotelStatus.ACTIVE,
        )

        val user = User(
            id = UserId(userId),
            username = UserName("테스트"),
        )

        userRepository.save(user)
        hotelRepository.saveHotel(hotel)

        // when
        mockMvc.get("/api/v1/hotels/my") {
            header("X-USER-ID", userId)
        } .andExpect {
            status { isOk() }

            jsonPath("$.userId") { value(userId) }
            jsonPath("$.username") { value("테스트") }
            jsonPath("$.hotelInfos") { isArray() }
            jsonPath("$.hotelInfos.length()") { value(1) }

            jsonPath("$.hotelInfos[0].hotelId") { value(hotel.id.id) }
            jsonPath("$.hotelInfos[0].name") { value("테스트 호텔") }
        }

        // then
        val savedHotels = hotelRepository.findByOwnerId(userId)
        assertThat(savedHotels).hasSize(1)
    }
}
