package com.musext.api

import com.musext.api.model.entity.Role
import com.musext.api.model.entity.User
import com.musext.api.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class ApiApplication {
	@Bean
	fun passwordEncoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}

	@Bean
	fun run(userService: UserService) = CommandLineRunner {
		userService.saveRole(Role("ROLE_USER"))
		userService.saveRole(Role("ROLE_MANAGER"))
		userService.saveRole(Role("ROLE_ADMIN"))
		userService.saveRole(Role("ROLE_SUPER_ADMIN"))

		userService.saveUser(User("John Travolta", "john", "1234")) //???????????
		userService.saveUser(User("Will Smith", "will", "1234"))
		userService.saveUser(User("Jim Carry", "jim", "1234"))
		userService.saveUser(User("Arnold Schwarzenegger", "arnold", "1234"))

		userService.addRoleToUser("john", "ROLE_USER")
		userService.addRoleToUser("john", "ROLE_MANAGER")
		userService.addRoleToUser("will", "ROLE_MANAGER")
		userService.addRoleToUser("jim", "ROLE_ADMIN")
		userService.addRoleToUser("arnold", "ROLE_SUPER_ADMIN")
		userService.addRoleToUser("arnold", "ROLE_ADMIN")
		userService.addRoleToUser("arnold", "ROLE_USER")
	}
}

fun main(args: Array<String>) {
	runApplication<ApiApplication>(*args)
}