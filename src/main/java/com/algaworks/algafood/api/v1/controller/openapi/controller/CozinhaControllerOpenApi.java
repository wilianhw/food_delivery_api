package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.core.springdoc.PageableParameter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface CozinhaControllerOpenApi {

    @PageableParameter
    PagedModel<CozinhaModel> listar(@Parameter(hidden = true) Pageable pageable);

    CozinhaModel buscar(Long cozinhaId);

    CozinhaModel criar(CozinhaInput cozinhaInput);

    CozinhaModel atualizar(
            Long cozinhaId,
            CozinhaInput cozinhaInput);

    void remover(Long cozinhaId);
}
