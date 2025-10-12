package com.dev.hotelmanagementservice.adapter.`in`.consumer

import com.dev.hotelmanagementservice.adapter.`in`.consumer.message.UserCreateMessage
import com.dev.hotelmanagementservice.adapter.json.JsonUtil
import com.dev.hotelmanagementservice.application.port.`in`.UserCreateUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Component

@Component
class UserConsumer (
    private val userCreateUseCase: UserCreateUseCase,
) {

    @KafkaListener (
        topics = ["user.create"],
        groupId = "user-create-group",
        concurrency = "3"
    )
    @RetryableTopic (
        dltTopicSuffix = ".dlt",
        attempts = "3",
        backoff = Backoff(delay = 1000, multiplier = 2.0)
    )
    fun userCreateEventConsumer(message: String) {
        // 유저 생성 메세지 객체 변환 (String -> Object)
        val userCreateMessage: UserCreateMessage = JsonUtil.decodeFromJson(message, UserCreateMessage.serializer())

        // 유저 생성
        userCreateUseCase.createUser(userCreateMessage)
    }
}