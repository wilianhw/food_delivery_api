package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.GrupoEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupo {

    private final GrupoRepository grupoRepository;

    public CadastroGrupo(GrupoRepository grupoRepository) {
        this.grupoRepository = grupoRepository;
    }

    public Grupo buscarOuFalhar(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void deletar(Long grupoId) {
        buscarOuFalhar(grupoId);

        try {
            grupoRepository.deleteById(grupoId);
        } catch (DataIntegrityViolationException e) {
            throw new GrupoEmUsoException(grupoId);
        }
    }

}
