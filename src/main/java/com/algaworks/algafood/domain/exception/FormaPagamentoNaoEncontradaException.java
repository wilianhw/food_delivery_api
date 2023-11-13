package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public FormaPagamentoNaoEncontradaException(Long formaPagamentoId) {
        super(String.format("Não foi encontrado forma de pagamento com código %d", formaPagamentoId));
    }
}
