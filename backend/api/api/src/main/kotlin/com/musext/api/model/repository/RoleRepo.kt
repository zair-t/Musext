package com.musext.api.model.repository

import com.musext.api.model.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepo: JpaRepository<Role, Long> {
    fun findByName(name: String): Role
}