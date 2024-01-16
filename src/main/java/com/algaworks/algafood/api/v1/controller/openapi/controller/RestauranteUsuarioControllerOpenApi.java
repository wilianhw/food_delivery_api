package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.UsuarioModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes")
public interface RestauranteUsuarioControllerOpenApi {

    @Operation(summary = "Lista todos os responsáveis pelo ID de um usuário")
    CollectionModel<UsuarioModel> listar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId);

    @Operation(summary = "Associa um restaurante a um usuário",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou usuário não foi encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    ResponseEntity<Void> associar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um usuário", example = "1", required = true) Long usuarioId
    );

    @Operation(summary = "Desassocia um restaurante a um usuário",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou usuário não foi encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    ResponseEntity<Void> desassociar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um usuário", example = "1", required = true) Long usuarioId
    );
}
