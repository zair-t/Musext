package com.musext.api.security

import com.musext.api.filter.CustomAuthenticationFilter
import com.musext.api.filter.CustomAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        http: HttpSecurity,
        bCryptPasswordEncoder: PasswordEncoder,
        userDetailsService: UserDetailsService
    ): AuthenticationManager? {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build()
    }

    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity, authenticationManager: AuthenticationManager): SecurityFilterChain? {
        val customAuthenticationFilter = CustomAuthenticationFilter(authenticationManager)
        customAuthenticationFilter.setFilterProcessesUrl("/api/login")
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeHttpRequests().requestMatchers("/api/login/**", "/api/token/refresh").permitAll()
        http.authorizeHttpRequests().requestMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER")
        http.authorizeHttpRequests().requestMatchers(GET, "/api/users/**").hasAnyAuthority("ROLE_ADMIN")
        http.authorizeHttpRequests().requestMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN")
        http.authorizeHttpRequests().anyRequest().authenticated()
        http.addFilter(customAuthenticationFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

}