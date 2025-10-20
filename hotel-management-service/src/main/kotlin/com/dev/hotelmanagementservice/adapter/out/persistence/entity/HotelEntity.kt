package com.dev.hotelmanagementservice.adapter.out.persistence.entity

import com.dev.hotelmanagementservice.domain.Address
import com.dev.hotelmanagementservice.domain.Description
import com.dev.hotelmanagementservice.domain.Email
import com.dev.hotelmanagementservice.domain.Hotel
import com.dev.hotelmanagementservice.domain.HotelId
import com.dev.hotelmanagementservice.domain.HotelName
import com.dev.hotelmanagementservice.domain.OwnerId
import com.dev.hotelmanagementservice.domain.PhoneNumber
import com.dev.hotelmanagementservice.domain.status.HotelStatus
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "hotels")
class HotelEntity (
    @Id
    @Column(name = "ulid", length = 26)
    val ulid: String,

    @Column(name = "owner_id", nullable = false, length = 26)
    val ownerId: String,

    @Column(nullable = false, length = 200)
    var name: String,

    @Column(length = 1000)
    var description: String? = null,

    @Embedded
    var addressEntity: AddressEntity,

    @Column(length = 20)
    var phoneNumber: String,

    @Column(length = 100)
    var email: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: HotelStatus = HotelStatus.ACTIVE,

    ) : BaseTimeEntity() {
    fun toDomain(): Hotel {
        return Hotel(
            id = HotelId(this.ulid),
            ownerId = OwnerId(this.ownerId),
            name = HotelName(this.name),
            description = this.description?.let { Description(it) },
            address = Address(
                country = this.addressEntity.country,
                city = this.addressEntity.city,
                street = this.addressEntity.street,
                zipCode = this.addressEntity.zipCode,
            ),
            phoneNumber = PhoneNumber(this.phoneNumber),
            email = this.email?.let { Email(it) },
            status = this.status,
        )
    }

    companion object {
        fun from(hotel: Hotel) : HotelEntity {
            return HotelEntity(
                hotel.id.id,
                hotel.ownerId.ownerId,
                hotel.name.name,
                hotel.description?.description,
                AddressEntity(
                    hotel.address.country,
                    hotel.address.city,
                    hotel.address.street,
                    hotel.address.zipCode,
                ),
                hotel.phoneNumber.phoneNumber,
                hotel.email?.email,
                hotel.status,
            )
        }
    }
}