package com.musext.backend.controller

import com.musext.backend.model.dto.mapper.toUserResponse
import com.musext.backend.model.dto.response.UserResponse
import com.musext.backend.model.entity.Music
import com.musext.backend.model.entity.User
import com.musext.backend.service.MusicService
import com.musext.backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService,
    val musicService: MusicService,
) {
    @GetMapping("/user/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = userService.findById(id)
        return if (user == null) ResponseEntity.notFound().build() else ResponseEntity.ok().body(user.toUserResponse())
    }

    @GetMapping("/users")
    fun get(): ResponseEntity<List<User>> {
        println("I was called!")
        return ResponseEntity.ok().body(userService.findAll())
    }

//    @GetMapping("/user/{id}")
//    fun getUser(@PathVariable id: Long): ResponseEntity<List<Music>> {
//        return ResponseEntity.ok(musicService.findByUserId(id))
//    }
}