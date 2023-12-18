package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    private final PermissaoModelAssembler permissaoModelAssembler;
    private final CadastroGrupoService cadastroGrupoService;
    private final AlgaLinks algaLinks;

    public GrupoPermissaoController(PermissaoModelAssembler permissaoModelAssembler, CadastroGrupoService cadastroGrupoService, AlgaLinks algaLinks) {
        this.permissaoModelAssembler = permissaoModelAssembler;
        this.cadastroGrupoService = cadastroGrupoService;
        this.algaLinks = algaLinks;
    }

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

    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupoService.associar(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociar(grupoId, permissaoId);

        return ResponseEntity.noContent().build();
    }
}
