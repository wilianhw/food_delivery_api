package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

    public static final String RESTAURANTE_NAO_ENCONTRADO = "Restaurante de código %d não foi encontrado";

    public RestauranteNaoEncontradoException(Long restauranteId) {
        super(String.format(RESTAURANTE_NAO_ENCONTRADO, restauranteId));
    }
}
