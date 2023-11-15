package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(Long usuarioId) {
        super(String.format("Usuário de código %d não foi encontrado", usuarioId));
    }
}
