package com.musext.backend.service

import com.musext.backend.model.dto.request.MusicRequest
import com.musext.backend.model.entity.Music
import org.springframework.web.multipart.MultipartFile

interface MusicService {
    fun findByUserId(userId: Long): List<Music>
    fun findAll(): List<Music>
    fun save(musicRequest: MusicRequest): Music
}