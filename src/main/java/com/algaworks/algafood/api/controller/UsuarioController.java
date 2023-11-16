package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioDisassembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

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

    @GetMapping
    public Collection<UsuarioModel> listar() {
        return usuarioModelAssembler.toCollectionModel(usuarioRepository.findAll());
    }

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

    @PutMapping("/{usuarioId}")
    public UsuarioModel atualizar(
            @PathVariable Long usuarioId,
            @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = cadastroUsuarioService.buscarOuFalhar(usuarioId);

        usuarioDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

        return usuarioModelAssembler.toModel(cadastroUsuarioService.salvar(usuarioAtual));
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId) {
        cadastroUsuarioService.deletar(usuarioId);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(
            @PathVariable Long usuarioId,
            @RequestBody @Valid SenhaInput senhaInput) {
        cadastroUsuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }
}
