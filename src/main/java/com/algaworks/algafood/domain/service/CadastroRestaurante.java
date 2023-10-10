package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestaurante {

    private final RestauranteRepository restauranteRepository;

    public CadastroRestaurante(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        Optional<Restaurante> optionalRestaurante = restauranteRepository.findById(restauranteId);

        if (optionalRestaurante.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format(
                    "Restaurante de código %d não foi encontrado", restauranteId
            ));
        }

        return optionalRestaurante.get();
    }
}
