package com.musext.api.service

import com.musext.api.model.entity.Role
import com.musext.api.model.entity.User

interface UserService {
    fun saveUser(user: User): User
    fun saveRole(role: Role): Role
    fun addRoleToUser(username: String, roleName: String)
    fun getUser(username: String): User?
    fun getUsers(): List<User>
}