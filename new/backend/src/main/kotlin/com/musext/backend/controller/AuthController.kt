package com.musext.backend.controller

import com.musext.backend.model.dto.mapper.toUserResponse
import com.musext.backend.model.dto.request.UserLoginRequest
import com.musext.backend.model.dto.request.UserRegisterRequest
import com.musext.backend.model.dto.response.UserResponse
import com.musext.backend.service.HashService
import com.musext.backend.service.TokenService
import com.musext.backend.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService,
) {
    @PostMapping("/login")
    fun login(@RequestBody payload: UserLoginRequest): ResponseEntity<UserResponse> {
        val user = userService.findByEmail(payload.email) ?: return ResponseEntity.status(400).build()

        if (!hashService.checkBcrypt(payload.password, user.password))
            return ResponseEntity.status(400).build()

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, tokenService.createToken(user))
            .body(user.toUserResponse());
    }

    @PostMapping("/register")
    fun register(@RequestBody payload: UserRegisterRequest): ResponseEntity<UserResponse> {
        if (userService.existsByEmail(payload.email))
            return ResponseEntity.status(400).build()

        payload.password = hashService.hashBcrypt(payload.password)

        val savedUser = userService.save(payload)

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, tokenService.createToken(savedUser))
            .body(savedUser.toUserResponse());

    }
}