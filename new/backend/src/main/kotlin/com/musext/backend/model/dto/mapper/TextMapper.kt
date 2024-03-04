package com.musext.backend.model.dto.mapper

import com.musext.backend.model.dto.request.TextRequest
import com.musext.backend.model.dto.response.TextResponse
import com.musext.backend.model.entity.Text

fun Text.toTextResponse(): TextResponse {
    return TextResponse(id = id, title = title, body = body, createdAt = createdAt)
}