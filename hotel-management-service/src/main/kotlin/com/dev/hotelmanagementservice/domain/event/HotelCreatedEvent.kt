package com.dev.hotelmanagementservice.domain.event

import com.dev.hotelmanagementservice.domain.Address
import java.time.LocalDateTime
import java.util.*

class HotelCreatedEvent (
    val ownerId: String,
    val hotelId: String,
    val hotelName: String,
    val description: String?,
    val address: Address,
    override val eventId: String = UUID.randomUUID().toString(),
    override val eventPublishTime: LocalDateTime = LocalDateTime.now()
) : DomainEvent