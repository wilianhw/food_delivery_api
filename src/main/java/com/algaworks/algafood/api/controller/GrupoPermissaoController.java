package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    private final PermissaoModelAssembler permissaoModelAssembler;
    private final CadastroGrupo cadastroGrupo;

    public GrupoPermissaoController(PermissaoModelAssembler permissaoModelAssembler, CadastroGrupo cadastroGrupo) {
        this.permissaoModelAssembler = permissaoModelAssembler;
        this.cadastroGrupo = cadastroGrupo;
    }

    @GetMapping
    public Collection<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupo.associar(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupo.desassociar(grupoId, permissaoId);
    }
}
