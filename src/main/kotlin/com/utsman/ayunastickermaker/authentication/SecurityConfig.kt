package com.utsman.ayunastickermaker.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.sessionManagement()
            .SessionFixationConfigurer().changeSessionId()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, *getPermit.toTypedArray()).permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic()

        return http.build()
    }

    @Bean
    fun userDetailService(): InMemoryUserDetailsManager {
        val username = System.getenv("USERNAME")
        val password = System.getenv("PASSWORD")

        val user = User
            .withUsername(username)
            .password(password)
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence?): String {
                return rawPassword.toString()
            }

            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                return rawPassword == encodedPassword
            }
        }
    }

    companion object {
        val getPermit = listOf(
            "/ping",
            "/start"
        )
    }
}