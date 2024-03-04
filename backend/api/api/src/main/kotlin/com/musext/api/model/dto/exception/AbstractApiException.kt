package com.musext.api.model.dto.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.musext.api.model.dto.ApiResponse
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonIgnoreProperties("cause", "stackTrace", "suppressed", "localizedMessage")
abstract class AbstractApiException(
    override val status: HttpStatus,
    override val message: String
) : ApiResponse, Exception() {
    override val timestamp: LocalDateTime = LocalDateTime.now()
}