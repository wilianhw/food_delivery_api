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

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/cidades")
public class CidadeController {

    private final CidadeModelAssembler cidadeModelAssembler;
    private final CidadeInputDisassembler cidadeInputDisassembler;
    public final CidadeRepository cidadeRepository;
    public final CadastroCidadeService cadastroCidadeService;

    public CidadeController(CidadeModelAssembler cidadeModelAssembler, CidadeInputDisassembler cidadeInputDisassembler, CidadeRepository cidadeRepository, CadastroCidadeService cadastroCidadeService) {
        this.cidadeModelAssembler = cidadeModelAssembler;
        this.cidadeInputDisassembler = cidadeInputDisassembler;
        this.cidadeRepository = cidadeRepository;
        this.cadastroCidadeService = cadastroCidadeService;
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(
            @PathVariable Long cidadeId) {
        return cidadeModelAssembler.toModel(cadastroCidadeService.buscarOuFalhar(cidadeId));
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping
    public CollectionModel<CidadeModel> listar() {
        return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeModel criar(
            @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

            CidadeModel cidadeModel = cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidade));

            ResourceUriHelper.addUriInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{cidadeId}")
    public CidadeModel atualizar(
            @PathVariable Long cidadeId,
            @RequestBody @Valid CidadeInput cidadeInput
    ) {
        Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

        try {
            return cidadeModelAssembler.toModel(cadastroCidadeService.salvar(cidadeAtual));
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @PathVariable Long cidadeId) {
        cadastroCidadeService.apagar(cidadeId);
    }
}
