package com.musext.backend.model.dto.request

import org.springframework.web.multipart.MultipartFile

data class MusicRequest(
    val userId: Long,
    val file: MultipartFile
)