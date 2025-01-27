package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Formas de pagamento", description = "Gerencia as formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

    @Operation(summary = "Lista todas as formas de pagamento")
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @Operation(summary = "Busca uma forma de pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "ID da forma de pagamento inválido",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forma de pagamento não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            }
    )
    FormaPagamentoModel buscar(
            @Parameter(description = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId);

    @Operation(summary = "Cria uma nova forma de pagamento")
    FormaPagamentoModel salvar(
            @RequestBody(description = "Representação da forma de pagamento")
            FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Atualiza uma forma de pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forma de pagamento não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            }
    )
    FormaPagamentoModel atualizar(
            @Parameter(description = "ID da forma de pagamento", example = "1", required = true)
            Long formaPagamentoId,
            @RequestBody(description = "Representação da forma de pagamento")
            FormaPagamentoInput formaPagamentoInput);

    @Operation(summary = "Exclui uma forma de pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Forma de pagamento não encontrada",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            }
    )
    void deletar(@Parameter(description = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
