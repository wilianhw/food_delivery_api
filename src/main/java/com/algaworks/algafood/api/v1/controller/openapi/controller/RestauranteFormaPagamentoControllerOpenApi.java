package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista as formas de pagamento de um restaurante por ID")
    CollectionModel<FormaPagamentoModel> listar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true)
            Long restauranteId);

    @Operation(summary = "Desassocia uma forma de pagamento de um restaurante",
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Restaurante ou forma de pagamento não encontrado não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema")))
            })
    ResponseEntity<Void> desassociar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

    @Operation(summary = "Associa uma forma de pagamento de um restaurante")
    ResponseEntity<Void> associar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
