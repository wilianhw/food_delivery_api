package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface UsuarioControllerOpenApi {
    CollectionModel<UsuarioModel> listar();

    UsuarioModel buscar(@PathVariable Long usuarioId);

    UsuarioModel cadastrar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput);

    UsuarioModel atualizar(
            @PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioInput usuarioInput);

    void deletar(@PathVariable Long usuarioId);

    void atualizarSenha(
            @PathVariable Long usuarioId,
            @RequestBody @Valid SenhaInput senhaInput);
}
