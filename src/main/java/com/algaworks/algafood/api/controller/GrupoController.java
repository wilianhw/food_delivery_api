package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.disassembler.GrupoDisassembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

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

    @GetMapping
    public Collection<GrupoModel> listar() {
        return grupoModelAssembler.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping("/{grupoId}")
    public GrupoModel buscar(@PathVariable Long grupoId) {
        return grupoModelAssembler.toModel(cadastroGrupoService.buscarOuFalhar(grupoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModel cadastrar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoDisassembler.toDomainObject(grupoInput);

        return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupo));
    }

    @PutMapping("/{grupoId}")
    public GrupoModel atualizar(
            @PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);

        grupoDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        return grupoModelAssembler.toModel(cadastroGrupoService.salvar(grupoAtual));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId) {
        cadastroGrupoService.deletar(grupoId);
    }
}
