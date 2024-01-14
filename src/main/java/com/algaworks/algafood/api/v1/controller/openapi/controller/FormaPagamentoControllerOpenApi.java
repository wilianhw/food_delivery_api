package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.ServletWebRequest;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@SecurityRequirement(name = "security_auth")
public interface FormaPagamentoControllerOpenApi {
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    FormaPagamentoModel buscar(@PathVariable Long formaPagamentoId);

    FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput);

    FormaPagamentoModel atualizar(
            @PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput);

    void deletar(@PathVariable Long formaPagamentoId);
}
