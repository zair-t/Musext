package com.musext.backend.model.dto.mapper

import com.musext.backend.model.dto.response.MusicResponse
import com.musext.backend.model.entity.Music

fun Music.toMusicResponse(): MusicResponse {
    return MusicResponse(id = id, artist = musicUser.name + ' ' + musicUser.surname, uri = uri,
        title = uri.substringAfterLast('/').substringBeforeLast('.')
    )
}