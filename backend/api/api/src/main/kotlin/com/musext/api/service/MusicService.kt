package com.musext.api.service

import com.musext.api.model.dto.request.MusicRequest
import com.musext.api.model.entity.Music

interface MusicService {
    fun getMusics(): Iterable<Music>
    fun getMusic(id: Long): Music
    fun saveMusic(request: MusicRequest): Music
    fun updateMusic(id: Long, request: MusicRequest): Music
    fun deleteMusic(id: Long)
}