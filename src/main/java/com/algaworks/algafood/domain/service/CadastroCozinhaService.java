package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

    private final CozinhaRepository cozinhaRepository;

    public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void remover(Long cozinhaId) {
        Cozinha cozinha = buscarOuFalhar(cozinhaId);

        try {
            cozinhaRepository.delete(cozinha);
            cozinhaRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(
                    "Cozinha de código %d não pode ser removida pois está em uso", cozinhaId
            ));
        }
    }
}
