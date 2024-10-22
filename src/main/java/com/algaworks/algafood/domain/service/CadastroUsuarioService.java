package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioEmUsoException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroUsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CadastroGrupoService cadastroGrupoService;
    private final PasswordEncoder passwordEncoder;

    public CadastroUsuarioService(UsuarioRepository usuarioRepository, CadastroGrupoService cadastroGrupoService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.cadastroGrupoService = cadastroGrupoService;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        if (usuarioExistente.isPresent() && usuarioExistente.get().equals(usuario))
            throw new NegocioException(String.format("Já existe usuário cadastro com o email %s", usuario.getEmail()));

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletar(Long usuarioId) {
        buscarOuFalhar(usuarioId);

        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new UsuarioEmUsoException(usuarioId);
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuarioAtual = buscarOuFalhar(usuarioId);

        if (!passwordEncoder.matches(senhaAtual, usuarioAtual.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuarioAtual.setSenha(novaSenha);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

        usuario.removerGrupo(grupo);
    }
}
