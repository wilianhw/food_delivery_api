package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CozinhaNaoEncontradaException(Long cidadeId) {
        super(String.format("Não existe cidade de código %d", cidadeId));
    }
}
