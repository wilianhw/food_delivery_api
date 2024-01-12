package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.exception.FormaPagamentoEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroFormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public CadastroFormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = formaPagamentoRepository;
    }

    public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    @Transactional
    public void deletar(Long formaPagamentoId) {
        buscarOuFalhar(formaPagamentoId);

        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
            formaPagamentoRepository.flush();

        } catch (DataIntegrityViolationException ex) {
            throw new FormaPagamentoEmUsoException(formaPagamentoId);
        }
    }
}
