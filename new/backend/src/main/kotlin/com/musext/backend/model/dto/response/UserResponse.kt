package com.musext.backend.model.dto.response

data class UserResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val login: String,
    val email: String,
    val age: Int,
    val gender: String,
)