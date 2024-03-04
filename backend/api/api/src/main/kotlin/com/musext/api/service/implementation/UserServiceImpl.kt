package com.musext.api.service.implementation

import com.musext.api.model.entity.Role
import com.musext.api.model.entity.User
import com.musext.api.model.repository.RoleRepo
import com.musext.api.model.repository.UserRepo
import com.musext.api.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

@Service
@Transactional
class UserServiceImpl(
    private val userRepo: UserRepo,
    private val roleRepo: RoleRepo,
    private val passwordEncoder: PasswordEncoder
) : UserService, UserDetailsService {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User? = userRepo.findByUsername(username)
        if (user == null) {
            log.error("User not found in the database")
            throw UsernameNotFoundException("")
        } else {
            log.info("User {} found in the database", username)
            var authorities: Collection<SimpleGrantedAuthority> = ArrayList<SimpleGrantedAuthority>()
            user.roles.forEach { authorities += SimpleGrantedAuthority(it.name) }
            return org.springframework.security.core.userdetails.User(user.username, user.password, authorities)
        }
    }

    override fun saveUser(user: User): User {
        log.info("Saving new user {} to the database", user.name)
        user.password = passwordEncoder.encode(user.password)
        return userRepo.save(user)
    }

    override fun saveRole(role: Role): Role {
        log.info("Saving new role {} to database", role.name)
        return roleRepo.save(role)
    }

    override fun addRoleToUser(username: String, roleName: String) {
        log.info("Adding role {} to user {}", roleName, username)
        val user: User? = userRepo.findByUsername(username)
        val role: Role = roleRepo.findByName(roleName)
        user!!.roles += role
    }

    override fun getUser(username: String): User? {
        log.info("Fetching user {}", username)
        return userRepo.findByUsername(username)
    }

    override fun getUsers(): List<User> {
        log.info("Fetching all users")
        return userRepo.findAll()
    }
}