package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public GrupoNaoEncontradoException(Long grupoId) {
        super(String.format("Não foi encontrado grupo de código %d", grupoId));
    }
}
