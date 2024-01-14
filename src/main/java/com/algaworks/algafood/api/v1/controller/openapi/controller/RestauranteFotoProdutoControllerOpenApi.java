package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface RestauranteFotoProdutoControllerOpenApi {
    FotoProdutoModel atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput
    ) throws IOException;

    FotoProdutoModel buscar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId
    );

    ResponseEntity<?> servirFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestHeader(name = "accept") String acceptHeader
    ) throws HttpMediaTypeNotAcceptableException;

    void deletar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId
    );

    default void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}
