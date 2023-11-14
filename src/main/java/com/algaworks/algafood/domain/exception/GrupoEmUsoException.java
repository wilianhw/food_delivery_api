package com.algaworks.algafood.domain.exception;

public class GrupoEmUsoException extends EntidadeEmUsoException {
    public GrupoEmUsoException(Long grupoId) {
        super(String.format("Grupo de código %d está em uso", grupoId));
    }
}
