package com.algaworks.algafood.api.v1.controller.openapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface FluxoPedidoControllerOpenApi {
    ResponseEntity<Void> confirmar(@PathVariable String codigoPedido);

    ResponseEntity<Void> entregar(@PathVariable String codigoPedido);

    ResponseEntity<Void> cancelar(@PathVariable String codigoPedido);
}
