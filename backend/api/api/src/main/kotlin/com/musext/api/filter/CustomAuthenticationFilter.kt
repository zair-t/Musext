package com.musext.api.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.HashMap

class CustomAuthenticationFilter(
    private val authManager: AuthenticationManager
): UsernamePasswordAuthenticationFilter() {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val username = request?.getParameter("username")
        val password = request?.getParameter("password")
        log.info("Username is: {}", username)
        log.info("password is: {}", password)
        val authenticationToken: UsernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(username, password)
        return authManager.authenticate(authenticationToken)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val user: User = authResult?.principal as User
        val algorithm: Algorithm = Algorithm.HMAC256("secret".toByteArray())
        val accessToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withIssuer(request?.requestURI.toString())
            .withClaim("roles", user.authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm)
        val refreshToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .withIssuer(request?.requestURI.toString())
            .sign(algorithm)
//        response?.setHeader("accesToken", accessToken)
//        response?.setHeader("refreshToken", refreshToken)
        val tokens: MutableMap<String, String> = HashMap<String, String>()
        tokens["accessToken"] = accessToken
        tokens["refreshToken"] = refreshToken
        response?.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response?.outputStream, tokens)
    }
}