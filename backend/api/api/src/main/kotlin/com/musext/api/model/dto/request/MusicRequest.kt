package com.musext.api.model.dto.request

import com.musext.api.model.entity.Music
import org.springframework.web.multipart.MultipartFile

class MusicRequest(
    val name: String,
    val author: String,
    val image: MultipartFile,
    val music: MultipartFile
) {
    fun asEntity() = Music(
        name = name,
        author = author,
        image = image.bytes,
        music = music.bytes
    )
}