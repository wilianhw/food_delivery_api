package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String message, Exception cause) {
        super(message, cause);
    }

    public NegocioException(String message) {
        super(message);
    }
}
