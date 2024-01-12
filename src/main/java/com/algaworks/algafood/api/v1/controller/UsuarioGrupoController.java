package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    private final GrupoModelAssembler grupoModelAssembler;
    private final CadastroUsuarioService cadastroUsuarioService;
    private final AlgaLinks algaLinks;

    public UsuarioGrupoController(GrupoModelAssembler grupoModelAssembler, CadastroUsuarioService cadastroUsuarioService, AlgaLinks algaLinks) {
        this.grupoModelAssembler = grupoModelAssembler;
        this.cadastroUsuarioService = cadastroUsuarioService;
        this.algaLinks = algaLinks;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable Long usuarioId) {
        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
                .add(algaLinks.linkToUsuarioGruposAssociacao(usuarioId, "associar"));

        gruposModel.getContent().forEach(grupoModel ->
                grupoModel.add(algaLinks.linkToUsuarioGruposDesassociacao(usuarioId, grupoModel.getId(), "usuario-grupos")));

        return gruposModel;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associarGrupo(
            @PathVariable Long usuarioId,
            @PathVariable Long grupoId) {
        cadastroUsuarioService.associarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociarGrupo(
            @PathVariable Long usuarioId,
            @PathVariable Long grupoId) {
        cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);

        return ResponseEntity.noContent().build();
    }
}
