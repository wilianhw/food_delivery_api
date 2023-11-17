package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedido;

    public FluxoPedidoService(EmissaoPedidoService emissaoPedido) {
        this.emissaoPedido = emissaoPedido;
    }

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        pedido.cancelar();
    }
}
