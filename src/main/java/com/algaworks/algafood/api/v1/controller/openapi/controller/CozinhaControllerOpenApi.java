package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {
    PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable);

    CozinhaModel buscar(@PathVariable Long cozinhaId);

    CozinhaModel criar(@RequestBody @Valid CozinhaInput cozinhaInput);

    CozinhaModel atualizar(
            @PathVariable Long cozinhaId,
            @RequestBody @Valid CozinhaInput cozinhaInput);

    void remover(@PathVariable Long cozinhaId);
}
