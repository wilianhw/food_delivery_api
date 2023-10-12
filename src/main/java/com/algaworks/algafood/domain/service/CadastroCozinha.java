package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroCozinha {

    private final CozinhaRepository cozinhaRepository;

    public CadastroCozinha(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(
                "Cozinha de código %d não encontrada", cozinhaId
        )));
    }

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void remover(Long cozinhaId) {
        Cozinha cozinha = buscarOuFalhar(cozinhaId);

        try {
            cozinhaRepository.delete(cozinha);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                    "Cozinha de código %d não pode ser removida pois está em uso", cozinhaId
            ));
        }
    }
}
