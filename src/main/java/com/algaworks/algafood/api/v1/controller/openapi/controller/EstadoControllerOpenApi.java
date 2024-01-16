package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Estado", description = "Gerencia estados")
public interface EstadoControllerOpenApi {

    @Operation(summary = "Busca estado por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do estado inválido",
                            content = {@Content(schema = @Schema(ref = "Problema"))}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estado não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    EstadoModel buscar(@Parameter(example = "1") Long estadoId);

    @Operation(summary = "Lista todos os estados")
    CollectionModel<EstadoModel> listar();

    @Operation(summary = "Cadastra um novo estado")
    EstadoModel cadastrar(EstadoInput estadoInput);

    @Operation(summary = "Busca estado por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do estado inválido",
                            content = {@Content(schema = @Schema(ref = "Problema"))}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estado não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    EstadoModel atualizar(
            @Parameter(example = "1") Long estadoId,
            EstadoInput estadoInput
    );

    @Operation(summary = "Busca estado por ID",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID do estado inválido",
                            content = {@Content(schema = @Schema(ref = "Problema"))}),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Estado não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    void deletar(@Parameter(example = "1") Long restauranteId);
}
