package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface CidadeControllerOpenApi {
    CidadeModel buscar(
            @PathVariable Long cidadeId);

    CollectionModel<CidadeModel> listar();

    CidadeModel criar(
            @RequestBody @Valid CidadeInput cidadeInput);

    CidadeModel atualizar(
            @PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInput cidadeInput
    );

    void deletar(
            @PathVariable Long cidadeId);
}
