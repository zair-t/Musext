package com.musext.backend.service.impl

import com.musext.backend.model.dto.mapper.toUser
import com.musext.backend.model.dto.request.UserRegisterRequest
import com.musext.backend.model.entity.User
import com.musext.backend.model.repository.UserRepository
import com.musext.backend.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepo: UserRepository
): UserService {
    override fun findById(id: Long): User? {
        return userRepo.findByIdOrNull(id)
    }

    override fun findByEmail(email: String): User? {
        return userRepo.findByEmail(email)
    }

    override fun findAll(): List<User> {
        return userRepo.findAll().toList()
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepo.existsByEmail(email)
    }

    override fun save(user: UserRegisterRequest): User {
        return userRepo.save(user.toUser())
    }
}