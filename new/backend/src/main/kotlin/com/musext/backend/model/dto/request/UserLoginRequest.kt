package com.musext.backend.model.dto.request

data class UserLoginRequest(
    val email: String,
    val password: String
)