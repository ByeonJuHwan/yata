package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Address(
    @Column(length = 100)
    val country: String,

    @Column(length = 100)
    val city: String,

    @Column(length = 200)
    val street: String,

    @Column(length = 20)
    val zipCode: String? = null
)
