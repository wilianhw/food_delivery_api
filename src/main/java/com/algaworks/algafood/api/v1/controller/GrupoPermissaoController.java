package com.algaworks.algafood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements com.algaworks.algafood.api.v1.controller.openapi.controller.GrupoPermissaoControllerOpenApi {

    private final PermissaoModelAssembler permissaoModelAssembler;
    private final CadastroGrupoService cadastroGrupoService;
    private final AlgaLinks algaLinks;

    public GrupoPermissaoController(PermissaoModelAssembler permissaoModelAssembler, CadastroGrupoService cadastroGrupoService, AlgaLinks algaLinks) {
        this.permissaoModelAssembler = permissaoModelAssembler;
        this.cadastroGrupoService = cadastroGrupoService;
        this.algaLinks = algaLinks;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoModel> permissoesCollectionModel =
                permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
                        .removeLinks()
                        .add(algaLinks.linkToGrupoPermissao(grupo.getId(), "grupo-permissoes"))
                        .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesCollectionModel.getContent().forEach(permissaoModel ->
                permissaoModel.add(algaLinks.linkToGrupoPermissaoDesssociacao(grupoId, "desassociar")));

        return permissoesCollectionModel;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupoService.associar(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociar(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}
