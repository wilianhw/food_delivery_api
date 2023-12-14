package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public Link linkToPedidos() {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidoUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(
                pedidoUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), LinkRelation.of("pedidos"));
    }

    public Link linkToRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel();
    }

    public Link linkToUsuarios(Long usuarioId) {
        return linkTo(methodOn(UsuarioController.class).buscar(usuarioId)).withSelfRel();
    }

    public Link linkToUsuarios() {
        return linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios");
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteUsuarioController.class).listar(restauranteId)).withSelfRel();
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId)).withRel("grupos-usuario");
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId)).withSelfRel();
    }

    public Link linkToCidades(Long cidadeId) {
        return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
    }

    public Link linkToCidades() {
        return linkTo(methodOn(CidadeController.class).listar()).withRel("cidades");
    }

    public Link linkToEstados(Long estadoId) {
        return linkTo(methodOn(EstadoController.class).buscar(estadoId)).withSelfRel();
    }

    public Link linkToEstados() {
        return linkTo(EstadoController.class).withRel("estados");
    }

    public Link linkToCozinhas() {
        return linkTo(CozinhaController.class).withRel("cozinhas");
    }

    public Link linkToItens(Long restauranteId, Long produtoId) {
        return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withSelfRel();
    }
}
