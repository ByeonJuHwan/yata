package com.dev.hotelmanagementservice.application.service.excpetion

class YataHotelException(
    private val codeInterFace: CodeInterFace,
    private val additionalMessage: String? = null,
) : RuntimeException(
    if (additionalMessage == null) {
        codeInterFace.message
    } else {
        "${codeInterFace.message} $additionalMessage"
    }
)