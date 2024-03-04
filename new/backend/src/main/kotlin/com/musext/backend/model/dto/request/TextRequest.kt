package com.musext.backend.model.dto.request

data class TextRequest(
    val userId: Long,
    val title: String,
    val body: String,
    val createdAt: String,
)