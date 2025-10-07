package com.dev.hotelmanagementservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
class HotelManagementServiceApplication

fun main(args: Array<String>) {
    runApplication<HotelManagementServiceApplication>(*args)
}
