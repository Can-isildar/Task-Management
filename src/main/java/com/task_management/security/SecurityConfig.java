package com.task_management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt kullanarak şifreleri hashlemek için
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // CSRF korumasını devre dışı bırak (isteğe bağlı)
                .authorizeRequests()
                .requestMatchers("/api/users", "/api/users/login").permitAll()  // Kayıt ve giriş işlemleri kimlik doğrulaması gerektirmez
                .anyRequest().authenticated()  // Diğer tüm istekler kimlik doğrulaması gerektirir
                .and()
                .formLogin().disable();  // Varsayılan form tabanlı giriş devre dışı bırakıldı

        return http.build();
    }
}

