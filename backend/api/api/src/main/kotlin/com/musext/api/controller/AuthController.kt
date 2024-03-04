package com.musext.api.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.musext.api.model.entity.Role
import com.musext.api.model.entity.User
import com.musext.api.service.UserService
import com.musext.api.utils.JWTUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

@RestController
@RequestMapping("/api")
class AuthController(
    private val userService: UserService
) {
    @GetMapping("/users")
    fun getUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

    @PostMapping("/user/save")
    fun saveUser(@RequestBody user: User): ResponseEntity<User> {
        val uri: URI =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString())
        return ResponseEntity.created(uri).body(userService.saveUser(user))
    }

    @PostMapping("/role/addtouser")
    fun addRoleToUser(@RequestBody form: RoleToUserForm): ResponseEntity<Unit> {
        userService.addRoleToUser(form.username, form.roleName)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/token/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authorizationHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val jwtUtil = JWTUtil(authorizationHeader)
                val user: User? = userService.getUser(jwtUtil.username)
                val accessToken: String = JWT.create()
                    .withSubject(user?.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.requestURI.toString())
                    .withClaim("roles", user?.roles?.stream()?.map(Role::name)?.collect(Collectors.toList()))
                    .sign(jwtUtil.algorithm)
                val tokens: MutableMap<String, String> = HashMap()
                tokens["accessToken"] = accessToken
                tokens["refreshToken"] = jwtUtil.token
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens)
            } catch (exception: Exception) {
                response.setHeader("error", exception.message)
                response.status = HttpStatus.FORBIDDEN.value()
                val error: MutableMap<String, String?> = HashMap()
                error["errorMessage"] = exception.message
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, error)
            }
        } else {
            throw java.lang.RuntimeException("Refresh token is missing");
        }
    }

    class RoleToUserForm {
        val username: String = ""
        val roleName: String = ""
    }
}