package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos", description = "Gerencia os grupos")
public interface GrupoControllerOpenApi {

    @Operation(summary = "Lista todos os grupos")
    CollectionModel<GrupoModel> listar();

    @Operation(summary = "Busca um grupo por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404",
                            description = "Grupo não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    GrupoModel buscar(@Parameter(description = "ID do grupo", example = "1", required = true) Long grupoId);

    @Operation(summary = "Cadastra um novo grupo")
    GrupoModel cadastrar(
            @RequestBody(description = "Representação de um grupo")
            GrupoInput grupoInput);

    @Operation(summary = "Atualiza um grupo existente por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404",
                            description = "Grupo não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    GrupoModel atualizar(
            @Parameter(description = "ID do grupo", example = "1", required = true) Long grupoId,
            @RequestBody(description = "Representação de um grupo")
            GrupoInput grupoInput);

    @Operation(summary = "Exclui um grupo por ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletar(@Parameter(description = "ID do grupo", example = "1", required = true) Long grupoId);
}
