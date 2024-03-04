package com.musext.api.model.dto.message

import com.musext.api.model.dto.ApiResponse
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

abstract class AbstractApiMessage(
    override val status: HttpStatus,
    override val message: String
) : ApiResponse {
    override val timestamp: LocalDateTime = LocalDateTime.now()
}