package com.algaworks.algafood.api.v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("/v1/pedidos/{codigoPedido}")
public class FluxoPedidoController implements com.algaworks.algafood.api.v1.controller.openapi.controller.FluxoPedidoControllerOpenApi {

    private final FluxoPedidoService fluxoPedido;

    public FluxoPedidoController(FluxoPedidoService fluxoPedido) {
        this.fluxoPedido = fluxoPedido;
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        fluxoPedido.confirmar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        fluxoPedido.entregar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        fluxoPedido.cancelar(codigoPedido);

        return ResponseEntity.noContent().build();
    }
}
