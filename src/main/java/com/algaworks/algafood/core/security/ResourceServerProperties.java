package com.algaworks.algafood.core.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("spring.security.oauth2.resourceserver.opaque-token")
public class ResourceServerProperties {

    @NotBlank
    private String introspectionUri;
    @NotBlank
    private String clientId;
    @NotBlank
    private String clientSecret;
}
