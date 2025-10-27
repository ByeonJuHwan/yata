package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.out.EventPublisher
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.dev.hotelmanagementservice.domain.User
import com.github.f4b6a3.ulid.UlidCreator
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Optional


@ExtendWith(MockKExtension::class)
class HotelWriteServiceTest {

    @InjectMockKs
    private lateinit var hotelWriteService: HotelWriteService

    @MockK
    private lateinit var hotelRepository: HotelRepository

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var eventPublisher: EventPublisher

    private val userId: String = UlidCreator.getUlid().toString()

    @Test
    fun `호텔 등록 성공`() {
        // given
        val request = RegisterHotelRequest(
            "테스트 호텔",
            null,
            "대한민국",
            "서울 특별시",
            "강남구",
            "123123",
            "01073225858",
            "test@test.com"
        )

        val mockUser = mockk<User>(relaxed = true)

        every { userRepository.findById(any()) } returns Optional.of(mockUser)
        every { hotelRepository.saveHotel(any()) } just Runs

        // when
        hotelWriteService.registerHotel(userId, request)

        // then
        verify(exactly = 1) { userRepository.findById(userId) }
        verify(exactly = 1) { hotelRepository.saveHotel(any()) }
    }
}