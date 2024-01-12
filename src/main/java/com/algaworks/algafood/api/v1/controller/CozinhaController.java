package com.algaworks.algafood.api.v1.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/cozinhas")
public class CozinhaController {

    private final CozinhaModelAssembler cozinhaModelAssembler;
    private final CozinhaInputDisassembler cozinhaInputDisassembler;
    private final CozinhaRepository cozinhaRepository;
    private final CadastroCozinhaService cadastroCozinhaService;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    public CozinhaController(CozinhaModelAssembler cozinhaModelAssembler, CozinhaInputDisassembler cozinhaInputDisassembler, CozinhaRepository cozinhaRepository, CadastroCozinhaService cadastroCozinhaService, PagedResourcesAssembler<Cozinha> pagedResourcesAssembler) {
        this.cozinhaModelAssembler = cozinhaModelAssembler;
        this.cozinhaInputDisassembler = cozinhaInputDisassembler;
        this.cozinhaRepository = cozinhaRepository;
        this.cadastroCozinhaService = cadastroCozinhaService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @CheckSecurity.Cozinhas.PodeConsultarCozinhas
    @GetMapping
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);

        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
    }

    @CheckSecurity.Cozinhas.PodeConsultarCozinhas
    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable Long cozinhaId) {
        return cozinhaModelAssembler.toModel(cadastroCozinhaService.buscarOuFalhar(cozinhaId));
    }

    @CheckSecurity.Cozinhas.PodeEditarCozinhas
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModel criar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);

        return cozinhaModelAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
    }

    @CheckSecurity.Cozinhas.PodeEditarCozinhas
    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(
            @PathVariable Long cozinhaId,
            @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        return cozinhaModelAssembler.toModel(cozinhaRepository.save(cozinhaAtual));
    }

    @CheckSecurity.Cozinhas.PodeEditarCozinhas
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cadastroCozinhaService.remover(cozinhaId);
    }
}
