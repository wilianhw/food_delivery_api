package com.algaworks.algafood.core.springdoc;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
@SecurityScheme(name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Algafood API")
                        .version("v1")
                        .description("REST API do AlgaFood")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.com")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("AlgaWorks")
                        .url("https://algaworks.com")
                );
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi
                .getPaths()
                .values()
                .stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    ApiResponses responses = operation.getResponses();

                    ApiResponse apiResponseNotFound = new ApiResponse().description("Recurso não encontrado");
                    ApiResponse apiResponseInternalError = new ApiResponse().description("Erro interno no servidor");
                    ApiResponse apiResponseWithoutRepresentation = new ApiResponse().description("Recurso não possui uma representação que poderia ser aceita pelo consumidor");

                    responses.addApiResponse("404", apiResponseNotFound);
                    responses.addApiResponse("406", apiResponseWithoutRepresentation);
                    responses.addApiResponse("500", apiResponseInternalError);
                });
    }
}
