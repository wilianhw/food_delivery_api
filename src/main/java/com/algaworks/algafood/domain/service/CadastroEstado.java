package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstado {

    private final EstadoRepository estadoRepository;

    public CadastroEstado(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public Estado buscarOuFalhar(Long restauranteId) {
        return estadoRepository.findById(restauranteId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(restauranteId));
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void apagar(Long estadoId) {
        buscarOuFalhar(estadoId);

        try {
            estadoRepository.deleteById(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Estado de código %d não pode ser removido pois está em uso", estadoId)
            );
        }
    }
}
