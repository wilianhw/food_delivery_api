package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.GrupoEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    private final GrupoRepository grupoRepository;
    private final CadastroPermissaoService cadastroPermissaoService;

    public CadastroGrupoService(GrupoRepository grupoRepository, CadastroPermissaoService cadastroPermissaoService) {
        this.grupoRepository = grupoRepository;
        this.cadastroPermissaoService = cadastroPermissaoService;
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

    @Transactional
    public void associar(Long grupoId, Long permissaoId) {
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);

        Grupo grupo = buscarOuFalhar(grupoId);

        grupo.associarPermissao(permissao);
    }

    @Transactional
    public void desassociar(Long grupoId, Long permissaoId) {
        Permissao permissao = cadastroPermissaoService.buscarOuFalhar(permissaoId);

        Grupo grupo = buscarOuFalhar(grupoId);

        grupo.desassociarPermissao(permissao);
    }
}
