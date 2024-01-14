package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.algaworks.algafood.api.v1.model.UsuarioModel;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface RestauranteUsuarioControllerOpenApi {
    CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId);

    ResponseEntity<Void> associar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    );

    ResponseEntity<Void> desassociar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    );
}
