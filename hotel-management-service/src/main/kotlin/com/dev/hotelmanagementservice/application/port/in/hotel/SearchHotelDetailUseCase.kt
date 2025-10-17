package com.dev.hotelmanagementservice.application.port.`in`.hotel

import com.dev.hotelmanagementservice.adapter.`in`.web.response.SearchHotelDetailResponse

interface SearchHotelDetailUseCase {
    fun searchHotelDetail(hotelId: String): SearchHotelDetailResponse

}