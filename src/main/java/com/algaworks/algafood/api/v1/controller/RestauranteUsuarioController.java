package com.algaworks.algafood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioController implements com.algaworks.algafood.api.v1.controller.openapi.controller.RestauranteUsuarioControllerOpenApi {

    private final UsuarioModelAssembler usuarioModelAssembler;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final AlgaLinks algaLinks;

    public RestauranteUsuarioController(UsuarioModelAssembler usuarioModelAssembler, CadastroRestauranteService cadastroRestauranteService, AlgaLinks algaLinks) {
        this.usuarioModelAssembler = usuarioModelAssembler;
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.algaLinks = algaLinks;
    }

    @CheckSecurity.Restaurantes.PodeConsultarRestaurantes
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        CollectionModel<UsuarioModel> usuariosRestaurante = usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToResponsavelRestauranteAssociar(restauranteId, "associar"));

        usuariosRestaurante.getContent().forEach(usuarioModel -> usuarioModel.add(algaLinks.linkToResponsavelRestauranteDesssociar(
                restauranteId, usuarioModel.getId(), "desassociar")));

        return usuariosRestaurante;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestauranteService.associarUsuario(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(
            @PathVariable Long restauranteId,
            @PathVariable Long usuarioId
    ) {
        cadastroRestauranteService.desassociarUsuario(restauranteId, usuarioId);

        return ResponseEntity.noContent().build();
    }
}
