package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.api.v1.model.GrupoModel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface UsuarioGrupoControllerOpenApi {
    CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId);

    ResponseEntity<Void> associarGrupo(
            @PathVariable Long usuarioId,
            @PathVariable Long grupoId);

    ResponseEntity<Void> desassociarGrupo(
            @PathVariable Long usuarioId,
            @PathVariable Long grupoId);
}
