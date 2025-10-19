package com.dev.hotelmanagementservice.adapter.`in`.web.reqeust

import com.dev.hotelmanagementservice.domain.Capacity
import java.math.BigDecimal

data class RegisterRoomRequest(
    val hotelId: String,
    val roomName: String,
    val roomType: String,
    val capacity: Int,
    val stock: Int,
    val basePrice: BigDecimal,
    val bedType: String,
)