package com.musext.api.controller

import com.musext.api.model.dto.ApiResponse
import com.musext.api.model.dto.message.DeletedMessage
import com.musext.api.model.dto.request.TextRequest
import com.musext.api.model.entity.Text
import com.musext.api.service.TextService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class TextController(
    private val textService: TextService
) {
    @GetMapping("/texts")
    fun getTexts(): Iterable<Text> = textService.getTexts()

    @PostMapping("/texts")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun saveText(@RequestBody request: TextRequest): Text = textService.saveText(request)

    @GetMapping("/texts/{id}")
    fun getText(@PathVariable id: Long): Text = textService.getText(id)

    @PutMapping("/texts/{id}")
    fun updateText(@PathVariable id: Long, @RequestBody request: TextRequest): Text =
        textService.updateText(id, request)

    @DeleteMapping("/texts/{id}")
    fun deleteText(@PathVariable id: Long): ResponseEntity<ApiResponse> {
        textService.deleteText(id)
        return DeletedMessage().asResponse()
    }
}