package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Restaurantes")
public interface RestauranteFotoProdutoControllerOpenApi {

    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    FotoProdutoModel atualizarFoto(
            @Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId,
            @RequestBody(required = true) FotoProdutoInput fotoProdutoInput
    ) throws IOException;

    @Operation(summary = "Busca foto do produto de um restaurante",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
                                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))

                            })
            })
    FotoProdutoModel buscar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId
    );

    @Operation(hidden = true)
    ResponseEntity<?> servirFoto(
            Long restauranteId,
            Long produtoId,
            String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Excluir uma foto do produto de um restaurante",
            responses = {
                    @ApiResponse(responseCode = "200",
                            content = {
                                    @Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoModel.class)),
                                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))

                            })
            })
    void deletar(
            @Parameter(description = "ID de um restaurante", example = "1", required = true) Long restauranteId,
            @Parameter(description = "ID de um produto", example = "1", required = true) Long produtoId
    );

    default void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}
