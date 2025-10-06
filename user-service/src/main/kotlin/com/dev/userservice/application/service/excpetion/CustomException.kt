package com.dev.userservice.application.service.excpetion

class CustomException(
    private val codeInterFace: CodeInterFace,
    private val additionalMessage: String? = null,
) : RuntimeException(
    if (additionalMessage == null) {
        codeInterFace.message
    } else {
        "${codeInterFace.message} $additionalMessage"
    }
)