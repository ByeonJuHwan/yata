package com.dev.hotelmanagementservice.adapter.`in`.web.response

data class SearchMyHotelResponse(
    val userId: String,
    val username: String,
    val hotelInfos: List<SearchMyHotelDetail>
)

data class SearchMyHotelDetail (
    val hotelId: String,
    val name: String,
)
