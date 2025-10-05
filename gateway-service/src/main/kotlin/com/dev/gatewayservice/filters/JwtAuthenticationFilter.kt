package com.dev.gatewayservice.filters

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class JwtAuthenticationFilter(
    @Value("\${jwt.secret-key}") private val secretKey: String,
) : AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config>(Config::class.java){

    override fun apply(config: Config?): GatewayFilter? {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request

            // Authorization 헤더 추출
            val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)

            // 헤더 검증
            if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                return@GatewayFilter exchange.response.setComplete()
            }

            val token = authHeader.substring(7)

            val secretKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
            val subject = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
                .subject

            val modifiedRequest = exchange
                .request
                .mutate()
                .header("X-User-Id", subject)
                .build()

            val modifiedExchange = exchange.mutate()
                .request(modifiedRequest)
                .build()

            chain.filter(modifiedExchange)
        }
    }


    class Config {
        // 필터 설정값
    }
}