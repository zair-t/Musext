package com.vaskir.jwt_auth.controllers

import com.vaskir.jwt_auth.dtos.LoginDTO
import com.vaskir.jwt_auth.dtos.Message
import com.vaskir.jwt_auth.dtos.RegisterDTO
import com.vaskir.jwt_auth.models.User
import com.vaskir.jwt_auth.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.util.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"))
@RestController
@RequestMapping("api")
class  AuthController(private val userService: UserService) {

    @PostMapping("register")
        fun register(@RequestBody body: RegisterDTO): ResponseEntity<User>{
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password
        return ResponseEntity.ok(this.userService.save(user))
        }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any>{
        val user = this.userService.findByEmail(body.email)
            ?:return ResponseEntity.badRequest().body(Message("user not found!"))

        if(!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body(Message("invalid password!"))
        }

        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60*24*1000)) //1 day
            .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512)).compact()

        val cookie = Cookie("jwt", jwt)
        cookie.isHttpOnly = true

        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))


    }

    @GetMapping("user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity.status(401).body(Message("unauthorised"))
            }

            val body = Jwts.parserBuilder().setSigningKey(Keys.secretKeyFor(SignatureAlgorithm.HS512)).build().parseClaimsJws(jwt).body

            return ResponseEntity.ok(this.userService.getById(body.issuer.toInt()))
        }catch(e: Exception){
            return ResponseEntity.status(401).body(Message("unauthorised"))
        }
    }
    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any>{
        var cookie = Cookie("jwt", "")
        cookie.maxAge = 0

        response.addCookie(cookie)

        return ResponseEntity.ok(Message("success"))

    }
}
