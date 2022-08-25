package com.utsman.ayunastickermaker.authentication

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.authentication.PasswordEncoderParser
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

// @Configuration
//public class BasicAuthWebSecurityConfiguration
//{
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//         .csrf().disable()
//         .authorizeRequests().anyRequest().authenticated()
//         .and()
//         .httpBasic();
//
//    return http.build();
//  }
//
//  @Bean
//  public InMemoryUserDetailsManager userDetailsService() {
//    UserDetails user = User
//        .withUsername("user")
//        .password("{noop}password")
//        .roles("USER")
//        .build();
//    return new InMemoryUserDetailsManager(user);
//  }
//}

@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.sessionManagement()
            .SessionFixationConfigurer().changeSessionId()
            .and()
            .csrf().disable()
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .httpBasic()

        return http.build()
    }

    @Bean
    fun userDetailService(): InMemoryUserDetailsManager {
        val user = User
            .withUsername("utsman")
            .password("1234")
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
}