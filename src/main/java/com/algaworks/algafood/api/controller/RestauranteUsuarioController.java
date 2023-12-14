package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

    private final UsuarioModelAssembler usuarioModelAssembler;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final AlgaLinks algaLinks;

    public RestauranteUsuarioController(UsuarioModelAssembler usuarioModelAssembler, CadastroRestauranteService cadastroRestauranteService, AlgaLinks algaLinks) {
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.algaLinks = algaLinks;
    }

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId));
    }

    @PutMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestauranteService.associarUsuario(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestauranteService.desassociarUsuario(restauranteId, usuarioId);
    }
}
