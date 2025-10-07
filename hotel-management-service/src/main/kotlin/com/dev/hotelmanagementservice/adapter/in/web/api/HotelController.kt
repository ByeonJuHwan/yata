package com.dev.hotelmanagementservice.adapter.`in`.web.api

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.RegisterHotelRequest
import com.dev.hotelmanagementservice.application.port.`in`.RegisterHotelUseCase
import jakarta.ws.rs.HeaderParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/hotels")
class HotelController (
    private val registerHotelUseCase: RegisterHotelUseCase,
) {


    @PostMapping
    fun registerHotel(
       @HeaderParam("X-USER-ID") userId: String,
       @RequestBody request: RegisterHotelRequest,
   ): ResponseEntity<Void> {
       registerHotelUseCase.registerHotel(userId, request)
       return ResponseEntity.status(HttpStatus.CREATED).build()
   }

}