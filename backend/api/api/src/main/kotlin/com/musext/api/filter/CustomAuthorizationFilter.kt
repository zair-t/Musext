package com.musext.api.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.musext.api.utils.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.juli.logging.LogFactory
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.util.Arrays.stream
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.MediaType

class CustomAuthorizationFilter : OncePerRequestFilter() {
    private val log = LogFactory.getLog(this::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.servletPath.equals("/api/login") || request.servletPath.equals("/api/token/refresh"))
            filterChain.doFilter(request, response)
        else {
            val authorizationHeader: String? = request.getHeader(AUTHORIZATION)
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    val jwtUtil = JWTUtil(authorizationHeader)
                    val roles: Array<String> = jwtUtil.decodedJWT.getClaim("roles").asArray(String::class.java)
                    var authorities: Collection<SimpleGrantedAuthority> = ArrayList()
                    stream(roles).forEach {
                        authorities += SimpleGrantedAuthority(it)
                    }
                    val authenticationToken =
                        UsernamePasswordAuthenticationToken(jwtUtil.username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (exception: Exception) {
                    log.error("Error logging in: ", exception)
                    response.setHeader("error", exception.message)
                    response.status = FORBIDDEN.value()
//                    response.sendError(FORBIDDEN.value())
                    val error: MutableMap<String, String?> = HashMap()
                    error["errorMessage"] = exception.message
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }

}