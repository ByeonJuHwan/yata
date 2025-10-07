package com.dev.hotelcatalogservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@EnableDiscoveryClient
@SpringBootApplication
class HotelCatalogServiceApplication

fun main(args: Array<String>) {
    runApplication<HotelCatalogServiceApplication>(*args)
}
