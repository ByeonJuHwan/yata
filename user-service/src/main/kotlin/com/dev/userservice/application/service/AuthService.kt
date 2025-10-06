package com.dev.userservice.application.service

import com.dev.userservice.application.port.out.OAuthServiceInterface

class AuthService (
    private val oAuth2Services: Map<String, OAuthServiceInterface>,
    private val jwtProvider: JwtProvider,
) {
}