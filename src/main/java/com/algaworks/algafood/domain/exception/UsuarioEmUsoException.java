package com.algaworks.algafood.domain.exception;

public class UsuarioEmUsoException extends EntidadeEmUsoException {
    public UsuarioEmUsoException(Long usuarioId) {
        super(String.format("Usuário de código %d está em uso", usuarioId));
    }
}
