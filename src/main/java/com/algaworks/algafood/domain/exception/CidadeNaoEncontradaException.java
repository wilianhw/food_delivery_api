package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    public static final String CIDADE_NAO_ENCONTRADA = "Não existe cidade de código %d";

    public CidadeNaoEncontradaException(Long cidadeId) {
        super(String.format(CIDADE_NAO_ENCONTRADA, cidadeId));
    }
}
