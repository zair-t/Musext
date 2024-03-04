package com.musext.backend.model.dto.response

data class MusicResponse(
    val id: Long,
    val title: String,
    val artist: String,
    val uri: String,
)