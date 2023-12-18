package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoModelAssembler permissaoModelAssembler;

    public PermissaoController(PermissaoRepository permissaoRepository, PermissaoModelAssembler permissaoModelAssembler) {
        this.permissaoRepository = permissaoRepository;
        this.permissaoModelAssembler = permissaoModelAssembler;
    }

    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> permissoes = permissaoRepository.findAll();

        return permissaoModelAssembler.toCollectionModel(permissoes);
    }
}
