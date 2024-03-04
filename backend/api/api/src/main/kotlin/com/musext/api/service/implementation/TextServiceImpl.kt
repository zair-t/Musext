package com.musext.api.service.implementation

import com.musext.api.model.dto.exception.ResourceNotFoundException
import com.musext.api.model.dto.request.TextRequest
import com.musext.api.model.entity.Text
import com.musext.api.model.repository.TextRepo
import com.musext.api.service.TextService
import org.springframework.stereotype.Service

@Service
class TextServiceImpl(
    private val textRepo: TextRepo
): TextService {
    override fun getTexts(): Iterable<Text> = textRepo.findAll()

    override fun getText(id: Long): Text = textRepo.findById(id).orElseThrow() { ResourceNotFoundException() }

    override fun saveText(request: TextRequest): Text = textRepo.save(request.asEntity())

    override fun updateText(id: Long, request: TextRequest): Text = getText(id).apply {
        title = request.title
        content = request.content
        textRepo.save(this)
    }

    override fun deleteText(id: Long) {
        if(!textRepo.existsById(id)) throw ResourceNotFoundException()
        textRepo.deleteById(id)
    }
}