package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.out.HotelRepository
import com.github.f4b6a3.ulid.UlidCreator
import org.hibernate.query.sqm.produce.function.StandardArgumentsValidators.exactly
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class HotelServiceTest {

    @InjectMocks
    private lateinit var hotelService: HotelService

    @Mock
    private lateinit var hotelRepository: HotelRepository

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
            null,
            "01073225858",
            null
        )


        // when
        hotelService.registerHotel(userId, request)

        // then
        verify(exactly(1)) { hotelRepository.saveHotel(any()) }
    }
}