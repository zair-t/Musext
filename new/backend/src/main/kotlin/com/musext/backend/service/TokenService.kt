package com.musext.backend.service

import com.musext.backend.model.entity.User

interface TokenService {
    fun createToken(user: User): String
    fun parseToken(token: String): User?
}