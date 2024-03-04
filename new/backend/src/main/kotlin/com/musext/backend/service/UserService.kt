package com.musext.backend.service

import com.musext.backend.model.dto.request.UserRegisterRequest
import com.musext.backend.model.entity.User

interface UserService {
    fun findById(id: Long): User?
    fun findByEmail(email: String): User?
    fun findAll(): List<User>
    fun existsByEmail(email: String): Boolean
    fun save(user: UserRegisterRequest): User
}