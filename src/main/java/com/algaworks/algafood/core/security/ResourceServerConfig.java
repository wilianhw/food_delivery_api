package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttp -> authorizeHttp
                        .anyRequest().authenticated())
                .oauth2ResourceServer(oauth2Resource ->
                        oauth2Resource.jwt(jwtConfigurer -> jwtConfigurer
                                        .jwkSetUri("http://localhost:8082/oauth2/jwks")
                ))
                .build();
    }
}
