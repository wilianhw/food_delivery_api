package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String ESTADO_NAO_ENCONTRADO = "Não existe estado de código %d";

    public EstadoNaoEncontradoException(Long estadoId) {
        super(String.format(ESTADO_NAO_ENCONTRADO, estadoId));
    }
}
