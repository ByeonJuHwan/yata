package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.RoomRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.Hotel
import com.dev.hotelmanagementservice.domain.HotelId
import com.dev.hotelmanagementservice.domain.HotelName
import com.dev.hotelmanagementservice.domain.User
import com.dev.hotelmanagementservice.domain.UserId
import com.dev.hotelmanagementservice.domain.UserName
import com.github.f4b6a3.ulid.UlidCreator
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional

@ExtendWith(MockKExtension::class)
class HotelReadServiceTest {

    @MockK
    private lateinit var hotelRepository: HotelRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var roomRepository: RoomRepository


    @InjectMockKs
    private lateinit var hotelReadService: HotelReadService

    private val userId: String = UlidCreator.getUlid().toString()

    @Test
    fun `내 호텔 목록이 비어있을 때`() {
        // given
        val user = mockk<User> {
            every { id } returns UserId(userId)
            every { username } returns UserName("테스트유저")
        }

        every { userRepository.findById(userId) } returns Optional.of(user)
        every { hotelRepository.findByOwnerId(userId) } returns emptyList()

        // when
        val result = hotelReadService.getMyHotels(userId)

        // then
        assertThat(result.userId).isEqualTo(userId)
        assertThat(result.username).isEqualTo("테스트유저")
        assertThat(result.hotelInfos).isEmpty()

        verify(exactly = 1) { userRepository.findById(userId) }
        verify(exactly = 1) { hotelRepository.findByOwnerId(userId) }
    }

    @Test
    fun `내 호텔 목록을 조회한다`() {
        // given
        val user = mockk<User>(relaxed = true) {
            every { id } returns UserId(userId)
            every { username } returns UserName("테스트유저")
        }

        val hotel1 = mockk<Hotel> {
            every { id } returns HotelId(UlidCreator.getUlid().toString())
            every { name } returns HotelName("호텔1")
        }

        val hotel2 = mockk<Hotel> {
            every { id } returns HotelId(UlidCreator.getUlid().toString())
            every { name } returns HotelName("호텔2")
        }

        val hotels = listOf(hotel1, hotel2)

        every { userRepository.findById(userId) } returns Optional.of(user)
        every { hotelRepository.findByOwnerId(userId) } returns hotels

        // when
        val result = hotelReadService.getMyHotels(userId)

        // then
        assertThat(result.userId).isEqualTo(userId)
        assertThat(result.username).isEqualTo("테스트유저")
        assertThat(result.hotelInfos).hasSize(2)
        assertThat(result.hotelInfos[0].hotelId).isEqualTo(hotel1.id.id)
        assertThat(result.hotelInfos[0].name).isEqualTo("호텔1")
        assertThat(result.hotelInfos[1].name).isEqualTo("호텔2")

        verify(exactly = 1) { userRepository.findById(userId) }
        verify(exactly = 1) { hotelRepository.findByOwnerId(userId) }
    }

}