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

import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.UsuarioDisassembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.model.input.SenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.model.input.UsuarioInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements com.algaworks.algafood.api.v1.controller.openapi.controller.UsuarioControllerOpenApi {

    private final UsuarioModelAssembler usuarioModelAssembler;
    private final UsuarioDisassembler usuarioDisassembler;
    private final UsuarioRepository usuarioRepository;
    private final CadastroUsuarioService cadastroUsuarioService;

    public UsuarioController(UsuarioModelAssembler usuarioModelAssembler, UsuarioDisassembler usuarioDisassembler, UsuarioRepository usuarioRepository, CadastroUsuarioService cadastroUsuarioService) {
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.usuarioDisassembler = usuarioDisassembler;
        this.usuarioRepository = usuarioRepository;
        this.cadastroUsuarioService = cadastroUsuarioService;
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping
    public CollectionModel<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeConsultar
    @GetMapping("/{usuarioId}")
    public UsuarioModel buscar(@PathVariable Long usuarioId) {
        return usuarioModelAssembler.toModel(cadastroUsuarioService.buscarOuFalhar(usuarioId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModel cadastrar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioDisassembler.toDomainObject(usuarioInput);

        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuario));
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(
            @PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        usuarioDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeEditar
    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId) {
        cadastroUsuarioService.deletar(usuarioId);
    }

    @CheckSecurity.UsuarioGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(
            @PathVariable Long usuarioId,
            @RequestBody @Valid SenhaInput senhaInput) {
        cadastroUsuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
}
