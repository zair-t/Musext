package com.vaskir.jwt_auth.repositories

import com.vaskir.jwt_auth.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByEmail(email:String): User?
}