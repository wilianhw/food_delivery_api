package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface GrupoControllerOpenApi {
    CollectionModel<GrupoModel> listar();

    GrupoModel buscar(@PathVariable Long grupoId);

    GrupoModel cadastrar(@RequestBody @Valid GrupoInput grupoInput);

    GrupoModel atualizar(
            @PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletar(@PathVariable Long grupoId);
}
