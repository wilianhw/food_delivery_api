package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestaurante {

    private final RestauranteRepository restauranteRepository;
    private final CadastroCozinha cadastroCozinha;

    public CadastroRestaurante(RestauranteRepository restauranteRepository, CadastroCozinha cadastroCozinha) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroCozinha = cadastroCozinha;
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

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }
}
