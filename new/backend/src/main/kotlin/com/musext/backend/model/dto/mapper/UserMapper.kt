package com.musext.backend.model.dto.mapper

import com.musext.backend.model.dto.request.UserRegisterRequest
import com.musext.backend.model.dto.response.UserResponse
import com.musext.backend.model.entity.User

fun User.toUserResponse(): UserResponse {
    return UserResponse(id, name, surname, login, email, age, gender)
}

fun UserRegisterRequest.toUser(): User {
    return User(name = name, surname = surname, login = login, email = email, age = age, gender = gender, password = password)
}