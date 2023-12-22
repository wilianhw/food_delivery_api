package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class ResourceServerConfig {

    private final ResourceServerProperties resourceServerProperties;

    public ResourceServerConfig(ResourceServerProperties resourceServerProperties) {
        this.resourceServerProperties = resourceServerProperties;
    }

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(token -> {
                    token.introspectionUri(resourceServerProperties.getIntrospectionUri());
                    token.introspectionClientCredentials(
                            resourceServerProperties.getClientId(),
                            resourceServerProperties.getClientSecret());
                }))
                .build();
    }
}
