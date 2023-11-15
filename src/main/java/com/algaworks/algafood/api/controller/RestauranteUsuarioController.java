package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestaurante;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

    private final UsuarioModelAssembler usuarioModelAssembler;
    private final CadastroRestaurante cadastroRestaurante;

    public RestauranteUsuarioController(UsuarioModelAssembler usuarioModelAssembler, CadastroRestaurante cadastroRestaurante) {
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.cadastroRestaurante = cadastroRestaurante;
    }

    @GetMapping
    public Collection<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios());
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestaurante.associarUsuario(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestaurante.desassociarUsuario(restauranteId, usuarioId);
    }
}
