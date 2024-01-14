package com.algaworks.algafood.core.springdoc;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.HashMap;
import java.util.Map;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
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

    public static final String NOT_FOUND_RESPONSE = "NotFoundResponse";
    public static final String BAD_REQUEST_RESPONSE = "BadRequestResponse";
    public static final String NOT_ACCEPTABLE_RESPONSE = "NotAcceptableResponse";
    public static final String INTERNAL_ERROR_RESPONSE = "InternalErrorResponse";

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
                ).components(new Components()
                        .schemas(gerarSchemas())
                        .responses(gerarResponses())
                );
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi
                .getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperationsMap()
                        .forEach((httpMethod, operation) -> {
                            ApiResponses responses = operation.getResponses();
                            responses.addApiResponse("500", new ApiResponse().$ref(INTERNAL_ERROR_RESPONSE));

                            switch (httpMethod) {
                                case GET -> {
                                    responses.addApiResponse("404", new ApiResponse().$ref(NOT_FOUND_RESPONSE));
                                    responses.addApiResponse("406", new ApiResponse().$ref(NOT_ACCEPTABLE_RESPONSE));
                                }
                                case POST ->
                                        responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
                                case PUT -> {
                                    responses.addApiResponse("400", new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
                                    responses.addApiResponse("404", new ApiResponse().$ref(NOT_FOUND_RESPONSE));
                                }
                                case DELETE ->
                                        responses.addApiResponse("404", new ApiResponse().$ref(NOT_FOUND_RESPONSE));
                                default -> {
                                }
                            }
                        }));
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<Problem>().$ref("Problema")));

        apiResponseMap.put(BAD_REQUEST_RESPONSE, new ApiResponse().description("Requisição inválida").content(content));
        apiResponseMap.put(NOT_FOUND_RESPONSE, new ApiResponse().description("Recurso não encontrado").content(content));
        apiResponseMap.put(NOT_ACCEPTABLE_RESPONSE, new ApiResponse().description("Recurso não possui representação que poderia ser aceita pelo consumidor").content(content));
        apiResponseMap.put(INTERNAL_ERROR_RESPONSE, new ApiResponse().description("Erro interno no servidor").content(content));

        return apiResponseMap;
    }
}
