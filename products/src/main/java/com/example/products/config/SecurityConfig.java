package com.example.products.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // API REST: sin CSRF ni sesiones
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints abiertos: obtener productos
                .requestMatchers(HttpMethod.GET, "/products", "/products/{id}").permitAll()
                // Todos los demas endpoints piden credenciales
                .anyRequest().authenticated()
            )
            // Autenticacion Basic con el usuario definido en application.properties
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
