package com.dev.hotelmanagementservice.application.port.`in`.room

import com.dev.hotelmanagementservice.adapter.`in`.web.reqeust.CreateReservationRequest
import com.dev.hotelmanagementservice.adapter.`in`.web.response.DeductRoomInventoryResponse

interface DeductRoomInventoryUseCase {
    fun deductRoomInventory(request: CreateReservationRequest): DeductRoomInventoryResponse
}