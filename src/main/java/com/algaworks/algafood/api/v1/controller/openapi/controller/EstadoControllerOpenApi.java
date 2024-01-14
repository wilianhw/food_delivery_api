package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenApi {
    EstadoModel buscar(@PathVariable Long estadoId);

    CollectionModel<EstadoModel> listar();

    EstadoModel cadastrar(@RequestBody @Valid EstadoInput estadoInput);

    EstadoModel atualizar(
            @PathVariable Long estadoId,
            @RequestBody @Valid EstadoInput estadoInput
    );

    void deletar(@PathVariable Long restauranteId);
}
