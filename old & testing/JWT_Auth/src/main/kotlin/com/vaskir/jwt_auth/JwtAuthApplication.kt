package com.vaskir.jwt_auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class JwtAuthApplication

fun main(args: Array<String>) {
    runApplication<JwtAuthApplication>(*args)
}
