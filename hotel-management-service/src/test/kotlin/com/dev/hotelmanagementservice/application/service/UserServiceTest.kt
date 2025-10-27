package com.dev.hotelmanagementservice.application.service

import com.dev.hotelmanagementservice.adapter.`in`.consumer.message.UserCreateMessage
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.github.f4b6a3.ulid.UlidCreator
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserServiceTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var userService: UserService

    private val userId = UlidCreator.getUlid().toString()

    @Test
    fun `유저를 생성한다`() {
        // given
        val message = UserCreateMessage (
            userId = userId,
            username = "test",
            eventId = userId
        )

        every { userRepository.save(any()) } just Runs

        // when
        userService.createUser(message)

        // then
        verify(exactly = 1) { userRepository.save(any()) }
    }

}