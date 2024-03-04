package com.musext.api.service

import com.musext.api.model.dto.request.TextRequest
import com.musext.api.model.entity.Text

interface TextService {
    fun getTexts(): Iterable<Text>
    fun getText(id: Long): Text
    fun saveText(request: TextRequest): Text
    fun updateText(id: Long, request: TextRequest): Text
    fun deleteText(id: Long)
}