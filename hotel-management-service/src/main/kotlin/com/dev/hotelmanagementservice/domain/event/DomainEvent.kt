package com.dev.hotelmanagementservice.domain.event

import java.time.LocalDateTime

interface DomainEvent {
    val eventId: String
    val eventPublishTime: LocalDateTime
}