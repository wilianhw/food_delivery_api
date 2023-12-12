package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController {

    private final UsuarioModelAssembler usuarioModelAssembler;
    private final CadastroRestauranteService cadastroRestauranteService;

    public RestauranteUsuarioController(UsuarioModelAssembler usuarioModelAssembler, CadastroRestauranteService cadastroRestauranteService) {
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.cadastroRestauranteService = cadastroRestauranteService;
    }

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(linkTo(methodOn(RestauranteUsuarioController.class)
                        .listar(restauranteId)).withSelfRel());
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
