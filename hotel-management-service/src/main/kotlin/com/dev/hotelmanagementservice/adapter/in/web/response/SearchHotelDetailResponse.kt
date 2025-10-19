package com.dev.hotelmanagementservice.adapter.`in`.web.response

import java.math.BigDecimal

data class SearchHotelDetailResponse(
    val hotelId: String,
    val hotelName: String,
    val roomResponses: List<SearchRoomDetailResponse>,
)

data class SearchRoomDetailResponse(
    val roomId: String,
    val roomName: String,
    val roomType: String,
    val capacity: Int,
    val stock: Int,
    val basePrice: BigDecimal,
    val bedType: String,
)
