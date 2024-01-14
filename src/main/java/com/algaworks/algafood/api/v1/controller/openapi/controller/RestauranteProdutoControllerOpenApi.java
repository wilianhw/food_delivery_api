package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenApi {
    CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId);

    ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId);

    ProdutoModel cadastrar(@PathVariable Long restauranteId, @RequestBody ProdutoInput produtoInput);

    ProdutoModel atualizar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody ProdutoInput produtoInput);
}
