package com.musext.api.service.implementation

import com.musext.api.model.dto.exception.ResourceNotFoundException
import com.musext.api.model.dto.request.MusicRequest
import com.musext.api.model.entity.Music
import com.musext.api.model.repository.MusicRepo
import com.musext.api.service.MusicService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class MusicServiceImpl(
    private val musicRepo: MusicRepo
): MusicService {
    override fun getMusics(): Iterable<Music> = musicRepo.findAll()

    override fun getMusic(id: Long): Music = musicRepo.findById(id).orElseThrow { ResourceNotFoundException() }

    override fun saveMusic(request: MusicRequest): Music = musicRepo.save(request.asEntity())

    override fun updateMusic(id: Long, request: MusicRequest): Music = getMusic(id).apply {
        name = request.name
        author = request.author
        image = request.image.bytes
        music = request.music.bytes
        musicRepo.save(this)
    }

    override fun deleteMusic(id: Long) {
        if(!musicRepo.existsById(id)) throw ResourceNotFoundException()
        musicRepo.deleteById(id)
    }
}