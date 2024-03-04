package com.musext.backend.service.impl

import com.musext.backend.service.HashService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class HashServiceImpl: HashService {
    override fun checkBcrypt(input: String, hash: String): Boolean  {
        return BCrypt.checkpw(input, hash)
    }

    override fun hashBcrypt(input: String): String  {
        return BCrypt.hashpw(input, BCrypt.gensalt(10))
    }
}