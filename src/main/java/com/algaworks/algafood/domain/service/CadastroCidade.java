package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroCidade {
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CadastroCidade(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cidade de código %d", cidadeId)));
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Optional<Estado> optionalEstado = estadoRepository.findById(estadoId);
        if (optionalEstado.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe estado de código %d", estadoId)
            );
        }
        return cidadeRepository.save(cidade);
    }

    public void apagar(Long cidadeId) {
        buscarOuFalhar(cidadeId);
        try {
            cidadeRepository.deleteById(cidadeId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser apagado pois está em uso", cidadeId)
            );
        }
    }
}
