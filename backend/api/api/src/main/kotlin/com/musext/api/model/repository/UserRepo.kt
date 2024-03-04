package com.musext.api.model.repository

import com.musext.api.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepo: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}