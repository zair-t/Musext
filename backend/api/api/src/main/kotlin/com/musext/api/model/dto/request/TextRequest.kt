package com.musext.api.model.dto.request

import com.musext.api.model.entity.Text

class TextRequest(
    val title: String,
    val content: String
) {
    fun asEntity() = Text(
        title = title,
        content = content
    )
}