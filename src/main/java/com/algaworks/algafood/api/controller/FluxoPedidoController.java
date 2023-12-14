package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedido;

    public FluxoPedidoController(FluxoPedidoService fluxoPedido) {
        this.fluxoPedido = fluxoPedido;
    }

    @PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigoPedido) {
        fluxoPedido.confirmar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigoPedido) {
        fluxoPedido.entregar(codigoPedido);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigoPedido) {
        fluxoPedido.cancelar(codigoPedido);

        return ResponseEntity.noContent().build();
    }
}
