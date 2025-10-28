package com.dev.hotelmanagementservice.domain.exception

class YataHotelException(
    val codeInterface: CodeInterFace,
    additionalMessage: String? = null,
) : RuntimeException(
    if (additionalMessage == null) {
        codeInterface.message
    } else {
        "${codeInterface.message} $additionalMessage"
    }
) {
    val code: Int
        get() = codeInterface.code
}