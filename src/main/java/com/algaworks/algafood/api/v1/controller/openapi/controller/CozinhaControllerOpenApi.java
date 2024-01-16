package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cozinhas", description = "Gerencia as cozinhas")
@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    @Operation(summary = "Lista todas as cozinhas")
    PagedModel<CozinhaModel> listar(@Parameter(hidden = true) Pageable pageable);

    @Operation(summary = "Busca uma cozinha por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cozinha não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    CozinhaModel buscar(@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

    @Operation(summary = "Cadastra uma nova cozinha")
    CozinhaModel criar(
            @RequestBody(description = "Representação de uma cozinha")
            CozinhaInput cozinhaInput);

    @Operation(summary = "Atualiza uma cozinha existente",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cozinha não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema")))})
    CozinhaModel atualizar(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true) Long cozinhaId,
            @RequestBody(description = "Representação de uma cozinha") CozinhaInput cozinhaInput);

    @Operation(summary = "Exclui uma cozinha por ID",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cozinha não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    void remover(
            @Parameter(description = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);
}
