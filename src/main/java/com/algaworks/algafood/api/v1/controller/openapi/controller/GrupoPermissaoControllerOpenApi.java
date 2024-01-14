package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.api.v1.model.PermissaoModel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface GrupoPermissaoControllerOpenApi {
    CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId);

    ResponseEntity<Void> associar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId);

    ResponseEntity<Void> desassociar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId);
}
