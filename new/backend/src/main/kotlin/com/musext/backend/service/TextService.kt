package com.musext.backend.service

import com.musext.backend.model.dto.request.TextRequest
import com.musext.backend.model.dto.response.TextResponse

interface TextService {
    fun get(): List<TextResponse>
    fun save(textRequest: TextRequest): TextResponse
}