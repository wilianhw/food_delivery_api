package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    private final PermissaoModelAssembler permissaoModelAssembler;
    private final CadastroGrupoService cadastroGrupoService;

    public GrupoPermissaoController(PermissaoModelAssembler permissaoModelAssembler, CadastroGrupoService cadastroGrupoService) {
        this.permissaoModelAssembler = permissaoModelAssembler;
        this.cadastroGrupoService = cadastroGrupoService;
    }

    @GetMapping
    public Collection<PermissaoModel> listar(@PathVariable Long grupoId) {
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupoService.associar(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(
            @PathVariable Long grupoId,
            @PathVariable Long permissaoId) {
        cadastroGrupoService.desassociar(grupoId, permissaoId);
    }
}
