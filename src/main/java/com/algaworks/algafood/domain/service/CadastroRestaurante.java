package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroRestaurante {

    private final RestauranteRepository restauranteRepository;
    private final CadastroCozinha cadastroCozinha;

    public CadastroRestaurante(RestauranteRepository restauranteRepository, CadastroCozinha cadastroCozinha) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroCozinha = cadastroCozinha;
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(
                        "Restaurante de código %d não foi encontrado", restauranteId)));
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        try {
            Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
            restaurante.setCozinha(cozinha);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }


        return restauranteRepository.save(restaurante);
    }

    public void apagar(Long restauranteId) {
        buscarOuFalhar(restauranteId);
        try {
            restauranteRepository.deleteById(restauranteId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Restaurante de código %d não pode ser apagado pois está em uso", restauranteId)
            );
        }
    }
}
