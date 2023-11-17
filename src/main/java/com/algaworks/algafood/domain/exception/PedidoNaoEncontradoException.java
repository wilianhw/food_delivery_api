package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("O pedido de código %s não foi encontrado", codigoPedido));
    }
}
