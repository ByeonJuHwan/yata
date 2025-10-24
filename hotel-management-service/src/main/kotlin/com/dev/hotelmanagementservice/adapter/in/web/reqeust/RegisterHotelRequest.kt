package com.dev.hotelmanagementservice.adapter.`in`.web.reqeust

data class RegisterHotelRequest(
    val name: String,
    val description: String?,
    val country: String,
    val city: String,
    val street: String,
    val zipCode: String,
    val phoneNumber: String,
    val email: String,
)
