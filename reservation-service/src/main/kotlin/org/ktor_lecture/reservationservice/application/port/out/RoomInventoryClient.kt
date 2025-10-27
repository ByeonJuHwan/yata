package org.ktor_lecture.reservationservice.application.port.out

import org.ktor_lecture.reservationservice.application.port.http.request.DeductInventoryRequest
import org.ktor_lecture.reservationservice.application.port.http.response.RoomDeductResponse

interface RoomInventoryClient {
    fun deductInventory(request: DeductInventoryRequest): RoomDeductResponse
}