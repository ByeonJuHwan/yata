package com.dev.hotelmanagementservice.application.port.out

import com.dev.hotelmanagementservice.domain.event.DomainEvent

interface EventPublisher {
    fun publish(event: DomainEvent)
}