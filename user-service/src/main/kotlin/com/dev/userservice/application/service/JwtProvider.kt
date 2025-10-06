package com.dev.userservice.application.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtProvider (
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.time}") private val time: Long,
) {

    private val ONE_MINUTE_TO_MILLIES: Long = 60 * 1000

    fun createToken(platform: String, email: String?, name: String?, id: String): String {


    }
}