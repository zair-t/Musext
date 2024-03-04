package com.musext.backend.service.impl

import com.musext.backend.model.entity.User
import com.musext.backend.service.TokenService
import com.musext.backend.service.UserService
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenServiceImpl(
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
    private val userService: UserService,
): TokenService {
    override fun createToken(user: User): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(user.login)
            .claim("userId", user.id)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    override fun parseToken(token: String): User? {
        return try {
            val jwt = jwtDecoder.decode(token)
            val userId = jwt.claims["userId"] as Long
            userService.findById(userId)
        } catch (e: Exception) {
            null
        }
    }
}