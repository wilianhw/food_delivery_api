package com.algaworks.algafood.domain.exception;

public class FormaPagamentoEmUsoException extends EntidadeEmUsoException {

    public FormaPagamentoEmUsoException(Long formaPagamentoId) {
        super(String.format("Forma de pagamento de código %d não pode ser apagada pois está em uso.", formaPagamentoId));
    }
}
