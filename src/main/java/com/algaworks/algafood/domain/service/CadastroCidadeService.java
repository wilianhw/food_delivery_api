package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.model.Estado;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroCidadeService {
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;

    public CadastroCidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
        this.cidadeRepository = cidadeRepository;
        this.estadoRepository = estadoRepository;
    }

    public Cidade buscarOuFalhar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Optional<Estado> optionalEstado = estadoRepository.findById(estadoId);

        if (optionalEstado.isEmpty()) throw new EstadoNaoEncontradoException(estadoId);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void apagar(Long cidadeId) {
        buscarOuFalhar(cidadeId);
        try {
            cidadeRepository.deleteById(cidadeId);
            cidadeRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Cidade de código %d não pode ser apagado pois está em uso", cidadeId)
            );
        }
    }
}
