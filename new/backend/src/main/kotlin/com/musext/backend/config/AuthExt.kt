package com.musext.backend.config

import com.musext.backend.model.entity.User
import org.springframework.security.core.Authentication

fun Authentication.toUser(): User {
    return principal as User
}