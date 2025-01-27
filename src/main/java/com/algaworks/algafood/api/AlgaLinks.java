package com.algaworks.algafood.api;

import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteFotoProdutoController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteUsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;

import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    public Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidoUrl = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(
                pedidoUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), LinkRelation.of(rel));
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return WebMvcLinkBuilder.linkTo(methodOn(FluxoPedidoController.class)
                .confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class)
                .cancelar(codigoPedido)).withRel(rel);
    }

    public Link linkToRestaurantes(Long restauranteId) {
        return linkTo(methodOn(RestauranteController.class).buscar(restauranteId)).withSelfRel();
    }

    public Link linkToRestaurantes(String rel) {
        return linkTo(RestauranteController.class).withRel(rel);
    }


    public Link linkToUsuarios(Long usuarioId) {
        return linkTo(methodOn(UsuarioController.class).buscar(usuarioId)).withSelfRel();
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(methodOn(UsuarioController.class).listar()).withRel(rel);
    }

    public Link linkToUsuarioGruposDesassociacao(Long usuarioId, Long grupoId, String rel) {
        return WebMvcLinkBuilder.linkTo(methodOn(UsuarioGrupoController.class)
                .desassociarGrupo(usuarioId, grupoId)).withRel(rel);
    }

    public Link linkToUsuarioGruposAssociacao(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .desassociarGrupo(usuarioId, null)).withRel(rel);
    }

    public Link linkToUsuarios() {
        return linkTo(methodOn(UsuarioController.class).listar()).withRel("usuarios");
    }

    public Link linkToResponsaveisRestaurante(Long restauranteId) {
        return linkTo(methodOn(RestauranteUsuarioController.class).listar(restauranteId)).withSelfRel();
    }

    public Link linkToResponsavelRestauranteAssociar(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public Link linkToResponsavelRestauranteDesssociar(Long restauranteId, Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioController.class)
                .desassociar(restauranteId, usuarioId)).withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioId) {
        return linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId)).withRel("grupos-usuario");
    }

    public Link linkToGrupos(String rel) {
        return linkTo(methodOn(GrupoController.class).listar()).withRel(rel);
    }

    public Link linkToGrupoPermissao(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).listar(grupoId)).withRel(rel);
    }

    public Link linkToGrupoPermissaoAssociacao(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .associar(grupoId, null)).withRel(rel);
    }

    public Link linkToGrupoPermissaoDesssociacao(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class)
                .desassociar(grupoId, null)).withRel(rel);
    }

    public Link linkToPermissoes(String rel) {
        return linkTo(methodOn(PermissaoController.class).listar()).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoId) {
        return linkTo(methodOn(FormaPagamentoController.class).buscar(formaPagamentoId)).withSelfRel();
    }

    public Link linkToFormaPagamento(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public Link linkToCidades(Long cidadeId) {
        return linkTo(methodOn(CidadeController.class).buscar(cidadeId)).withSelfRel();
    }

    public Link linkToCidades(String rel) {
        return linkTo(methodOn(CidadeController.class).listar()).withRel(rel);
    }

    public Link linkToEstado(Long estadoId) {
        return WebMvcLinkBuilder.linkTo(methodOn(EstadoController.class).buscar(estadoId)).withSelfRel();
    }

    public Link linkToEstado(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToCozinhas(Long cozinhaId) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId)).withSelfRel();
    }

    public Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withSelfRel();
    }

    public Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToFotoProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteFotoProdutoController.class).buscar(restauranteId, produtoId)).withRel(rel);
    }

    public Link linkToRestaurnateFormasPagamento(Long restauranteId, String rel) {
        return WebMvcLinkBuilder.linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteId)).withRel(rel);
    }

    public Link linkToRestaurnateFormasPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteId, formaPagamentoId)).withRel(rel);
    }

    public Link linkToRestaurnateFormasPagamentoAssociacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteId, null)).withRel(rel);
    }

    public Link linkToInativarRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .inativar(restauranteId)).withRel(rel);
    }

    public Link linkToAtivarRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .ativar(restauranteId)).withRel(rel);
    }

    public Link linkToAbrirRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .fechar(restauranteId)).withRel(rel);
    }

    public Link linkToFecharRestaurante(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .abrir(restauranteId)).withRel(rel);
    }

    public Link linkToEstatisticas(String rel) {
        return linkTo(EstatisticasController.class).withRel(rel);
    }

    public Link linkToEstatisticasVendasDiarias(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM)
        );

        String pedidoUrl = linkTo(methodOn(EstatisticasController.class)
                .consultarVendasDiarias(null)).toUri().toString();

        return Link.of(UriTemplate.of(pedidoUrl, filtroVariables), rel);
    }
}
