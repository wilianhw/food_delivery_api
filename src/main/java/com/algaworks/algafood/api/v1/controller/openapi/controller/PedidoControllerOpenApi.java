package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface PedidoControllerOpenApi {
    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);

    PedidoModel buscar(@PathVariable String codigoPedido);

    PedidoModel cadastrar(@RequestBody PedidoInput pedidoInput);
}
