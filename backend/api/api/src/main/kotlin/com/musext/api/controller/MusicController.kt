package com.musext.api.controller

import com.musext.api.model.dto.ApiResponse
import com.musext.api.model.dto.message.DeletedMessage
import com.musext.api.model.dto.request.MusicRequest
import com.musext.api.model.entity.Music
import com.musext.api.service.MusicService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class MusicController(
    private val musicService: MusicService
) {
    @GetMapping("/musics")
    fun getMusics(): Iterable<Music> = musicService.getMusics()

    @PostMapping("/musics")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun saveMusic(@ModelAttribute request: MusicRequest): Music = musicService.saveMusic(request)

    @GetMapping("/musics/{id}")
    fun getMusic(@PathVariable id: Long): Music = musicService.getMusic(id)

    @PutMapping("/musics/{id}")
    fun updateMusic(@PathVariable id: Long, @RequestBody request: MusicRequest): Music =
        musicService.updateMusic(id, request)

    @DeleteMapping("/musics/{id}")
    fun deleteMusic(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        musicService.deleteMusic(id)
        return DeletedMessage().asResponse()
    }
}