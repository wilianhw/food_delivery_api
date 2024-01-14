package com.algaworks.algafood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.GrupoDisassembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/grupos")
public class GrupoController implements com.algaworks.algafood.api.v1.controller.openapi.controller.GrupoControllerOpenApi {

    private final GrupoModelAssembler grupoModelAssembler;
    private final GrupoDisassembler grupoDisassembler;
    private final GrupoRepository grupoRepository;
    private final CadastroGrupoService cadastroGrupoService;

    public GrupoController(GrupoModelAssembler grupoModelAssembler, GrupoDisassembler grupoDisassembler, GrupoRepository grupoRepository, CadastroGrupoService cadastroGrupoService) {
        this.grupoModelAssembler = grupoModelAssembler;
        this.grupoDisassembler = grupoDisassembler;
        this.grupoRepository = grupoRepository;
        this.cadastroGrupoService = cadastroGrupoService;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        return grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoId));
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel cadastrar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoDisassembler.toDomainObject(grupoInput);

        return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(
            @PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);

        grupoDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId) {
        cadastroGrupoService.deletar(grupoId);
    }
}
