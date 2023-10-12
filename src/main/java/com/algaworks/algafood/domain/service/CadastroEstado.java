package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroEstado {

    private final EstadoRepository estadoRepository;

    public CadastroEstado(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Estado buscarOuFalhar(Long restauranteId) {
        return estadoRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Estado com código %d não encontrado", restauranteId)));
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void apagar(Estado estado) {
        try {
            estadoRepository.delete(estado);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido pois está em uso", estado.getId())
            );
        }
    }
}
