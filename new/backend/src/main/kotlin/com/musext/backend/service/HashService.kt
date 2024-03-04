package com.musext.backend.service

interface HashService {
    fun checkBcrypt(input: String, hash: String): Boolean
    fun hashBcrypt(input: String): String
}