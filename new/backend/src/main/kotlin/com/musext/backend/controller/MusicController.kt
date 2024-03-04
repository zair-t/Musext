package com.musext.backend.controller

import com.musext.backend.model.dto.mapper.toMusicResponse
import com.musext.backend.model.dto.request.MusicRequest
import com.musext.backend.model.dto.response.MusicResponse
import com.musext.backend.model.entity.Music
import com.musext.backend.service.MusicService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MusicController(
    val musicService: MusicService,
) {
    @GetMapping("/musics")
    fun get(): ResponseEntity<List<MusicResponse>> {
        return ResponseEntity.ok().body(musicService.findAll().map {music: Music ->  music.toMusicResponse()})
    }

    @PostMapping("/music")
    fun create(
        @ModelAttribute musicRequest: MusicRequest
    ): ResponseEntity<MusicResponse> {
        return ResponseEntity.ok().body(musicService.save(musicRequest).toMusicResponse())
    }
}