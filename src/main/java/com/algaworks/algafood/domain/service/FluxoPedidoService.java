package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedido;

    public FluxoPedidoService(EmissaoPedidoService emissaoPedido) {
        this.emissaoPedido = emissaoPedido;
    }

    @Transactional
    public void confirmar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(getMessageError(pedido, StatusPedido.CRIADO));
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(LocalDateTime.now());
    }

    @Transactional
    public void entregar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
            throw new NegocioException(getMessageError(pedido, StatusPedido.ENTREGUE));
        }
        pedido.setStatus(StatusPedido.ENTREGUE);
        pedido.setDataEntrega(LocalDateTime.now());
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(pedidoId);

        if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
            throw new NegocioException(getMessageError(pedido, StatusPedido.CANCELADO));
        }
        pedido.setStatus(StatusPedido.CANCELADO);
        pedido.setDataCancelamento(LocalDateTime.now());
    }

    private String getMessageError(Pedido pedido, StatusPedido status) {
        return String.format("Status do pedido %d não pode ser alterado de %s para %s",
                pedido.getId(), pedido.getStatus().getDescricao(), status.getDescricao());
    }
}
