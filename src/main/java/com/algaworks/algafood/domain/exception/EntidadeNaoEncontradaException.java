package com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

    protected EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
