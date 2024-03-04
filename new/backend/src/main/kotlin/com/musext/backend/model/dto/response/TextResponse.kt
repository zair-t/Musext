package com.musext.backend.model.dto.response

data class TextResponse(
    val id: Long,
    val title: String,
    val body: String,
    val createdAt: String
)