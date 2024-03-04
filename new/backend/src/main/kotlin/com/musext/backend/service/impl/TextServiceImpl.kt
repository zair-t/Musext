package com.musext.backend.service.impl

import com.musext.backend.model.dto.mapper.toTextResponse
import com.musext.backend.model.dto.request.TextRequest
import com.musext.backend.model.dto.response.TextResponse
import com.musext.backend.model.entity.Text
import com.musext.backend.model.repository.TextRepository
import com.musext.backend.model.repository.UserRepository
import com.musext.backend.service.TextService
import org.springframework.stereotype.Service

@Service
class TextServiceImpl(
    private val textRepository: TextRepository,
    private val userRepository: UserRepository
) : TextService {
    override fun get(): List<TextResponse> {
        return textRepository.findAll().reversed().map { text -> text.toTextResponse() }
    }

    override fun save(textRequest: TextRequest): TextResponse {
        val user = userRepository.findById(textRequest.userId)
        return textRepository.save(
            Text(
                title = textRequest.title, body = textRequest.body,
                createdAt = textRequest.createdAt, textUser = user.get()
            )
        ).toTextResponse()
    }

}