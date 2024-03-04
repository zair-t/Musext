package com.musext.backend.model.dto.request

data class UserRegisterRequest(
    val name: String,
    val surname: String,
    val login: String,
    val email: String,
    val age: Int,
    var password: String,
    val gender: String,
)