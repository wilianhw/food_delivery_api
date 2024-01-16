package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos", description = "Gerencia pedidos")
public interface FluxoPedidoControllerOpenApi {


    @Operation(summary = "Confirmação de pedido pelo seu código", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> confirmar(
            @Parameter(example = "d80893e4-f7c0-44f1-bcae-258c5a14370d")
            String codigoPedido);

    @Operation(summary = "Entrega de pedido pelo seu código", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> entregar(
            @Parameter(example = "d80893e4-f7c0-44f1-bcae-258c5a14370d")
            String codigoPedido);


    @Operation(summary = "Cancelamento de pedido pelo seu código", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    ResponseEntity<Void> cancelar(
            @Parameter(example = "d80893e4-f7c0-44f1-bcae-258c5a14370d")
            String codigoPedido);
}
