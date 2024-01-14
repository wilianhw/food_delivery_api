package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements com.algaworks.algafood.api.v1.controller.openapi.controller.PermissaoControllerOpenApi {

    private final PermissaoRepository permissaoRepository;
    private final PermissaoModelAssembler permissaoModelAssembler;

    public PermissaoController(PermissaoRepository permissaoRepository, PermissaoModelAssembler permissaoModelAssembler) {
        this.permissaoRepository = permissaoRepository;
        this.permissaoModelAssembler = permissaoModelAssembler;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoModel> listar() {
        List<Permissao> permissoes = permissaoRepository.findAll();

        return permissaoModelAssembler.toCollectionModel(permissoes);
    }
}
