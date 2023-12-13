package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    private final ModelMapper modelMapper;

    public PedidoModelAssembler(ModelMapper modelMapper) {
        super(PedidoController.class, PedidoModel.class);
        this.modelMapper = modelMapper;
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoModel.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        pedidoModel.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId())).withSelfRel());

        pedidoModel.getEndereco().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedido.getEndereco().getCidade().getId())).withSelfRel());

        pedidoModel.getItens().forEach(item -> item.add(linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(pedido.getRestaurante().getId(), item.getProdutoId())).withSelfRel()));

        return pedidoModel;
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toModel).toList();
    }
}
