package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
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

        CollectionModel<UsuarioModel> usuariosRestaurante = usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToResponsavelRestauranteAssociar(restauranteId, "associar"));

        usuariosRestaurante.getContent().forEach(usuarioModel -> usuarioModel.add(algaLinks.linkToResponsavelRestauranteDesssociar(
                restauranteId, usuarioModel.getId(), "desassociar")));

        return usuariosRestaurante;
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestauranteService.associarUsuario(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestauranteService.desassociarUsuario(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}
