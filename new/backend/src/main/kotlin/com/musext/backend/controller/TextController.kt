package com.musext.backend.controller

import com.musext.backend.model.dto.request.TextRequest
import com.musext.backend.model.dto.response.TextResponse
import com.musext.backend.service.TextService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class TextController(
    private val textService: TextService
) {
    @GetMapping("/texts")
    fun get(): ResponseEntity<List<TextResponse>> {
        return ResponseEntity.ok().body(textService.get())
    }

    @PostMapping("/text")
    fun create(
        @RequestBody text: TextRequest
    ): ResponseEntity<TextResponse> {
        return ResponseEntity.ok().body(textService.save(text))
    }

}