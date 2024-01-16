package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Produtos", description = "Gerencia os produtos de um restaurante")
public interface RestauranteProdutoControllerOpenApi {

    @Operation(summary = "Lista os produtos de um restaurante")
    CollectionModel<ProdutoModel> listar(
            @Parameter(description = "ID de um restaurante") Long restauranteId);

    @Operation(summary = "Busca um produto de um restaurante",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
                            @Content(schema = @Schema(ref = "Problema"))}),
                    @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                            @Content(schema = @Schema(ref = "Problema"))})
            })
    ProdutoModel buscar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId);

    @Operation(summary = "Cadastra um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ProdutoModel cadastrar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @RequestBody(description = "Representação de um produto") ProdutoInput produtoInput);


    @Operation(summary = "Atualiza um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ProdutoModel atualizar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId,
            @RequestBody(description = "Representação de um produto") ProdutoInput produtoInput);
}
