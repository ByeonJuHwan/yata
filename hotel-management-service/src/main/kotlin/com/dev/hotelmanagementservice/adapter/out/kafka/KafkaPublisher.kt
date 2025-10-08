package com.dev.hotelmanagementservice.adapter.out.kafka

import com.dev.hotelmanagementservice.application.port.out.EventPublisher
import com.dev.hotelmanagementservice.domain.event.DomainEvent
import org.springframework.stereotype.Component

@Component
class KafkaPublisher(

) : EventPublisher {
    override fun publish(event: DomainEvent) {
        TODO("Not yet implemented")
    }
}