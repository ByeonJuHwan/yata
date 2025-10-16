package com.dev.hotelmanagementservice.adapter.`in`.consumer

import com.dev.hotelmanagementservice.IntegrationTestBase
import com.dev.hotelmanagementservice.adapter.`in`.consumer.message.UserCreateMessage
import com.dev.hotelmanagementservice.application.port.out.UserRepository
import com.github.f4b6a3.ulid.UlidCreator
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.awaitility.Awaitility.await
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import java.time.Duration
import kotlin.test.Test

class UserConsumerTest : IntegrationTestBase() {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>

    @Autowired
    private lateinit var userRepository: UserRepository

    private val userId = UlidCreator.getUlid().toString()

    @Test
    fun `Kafka 메시지 발행 시 Consumer가 수신하여 User 를 저장한다`() {
        // given
        val username = "test-name"

        val message = UserCreateMessage(
            eventId = userId,
            userId = userId,
            username = username
        )

        // When
        kafkaTemplate.send("user.create", toJson(message)).get()

        // then
        await()
            .atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(100))
            .untilAsserted {
                val savedUser = userRepository.findByUserName(username)
                assertThat(savedUser).isPresent
                assertThat(savedUser.get().username.username).isEqualTo(username)
            }
    }

}