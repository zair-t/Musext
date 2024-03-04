package com.musext.api.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT

class JWTUtil(authorizationHeader: String) {
    var token: String
    var algorithm: Algorithm
    var verifier: JWTVerifier
    var decodedJWT: DecodedJWT
    var username: String

    init {
        token = authorizationHeader.substring("Bearer ".length)
        algorithm = Algorithm.HMAC256("secret".toByteArray())
        verifier = JWT.require(algorithm).build()
        decodedJWT = verifier.verify(token)
        username = decodedJWT.subject
    }
}
