package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.status.HotelStatus
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id

@Entity
class HotelEntity (
    @Id
    @Column(name = "ulid", length = 26)
    val ulid: String,

    @Column(nullable = false, length = 200)
    var name: String,

    @Column(length = 1000)
    var description: String? = null,

    @Embedded
    var address: Address,

    @Column(length = 20)
    var phoneNumber: String? = null,

    @Column(length = 100)
    var email: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: HotelStatus = HotelStatus.ACTIVE,

) : BaseTimeEntity() {
}