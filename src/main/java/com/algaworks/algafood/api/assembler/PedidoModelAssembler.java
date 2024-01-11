package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public PedidoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(PedidoController.class, PedidoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoModel);

        pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (pedido.podeSerConfirmado()) {
                pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));
            }

            if (pedido.podeSerCancelado()) {
                pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));
            }

            if (pedido.podeSerEntregue()) {
                pedidoModel.add(algaLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
            }
        }

        pedidoModel.add(algaLinks.linkToRestaurantes(pedido.getRestaurante().getId()));

        pedidoModel.add(algaLinks.linkToUsuarios(pedido.getCliente().getId()));

        pedidoModel.add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        pedidoModel.add(algaLinks.linkToCidades(pedido.getEndereco().getCidade().getId()));


        pedidoModel.getItens().forEach(item -> algaLinks.linkToProduto(
                pedido.getRestaurante().getId(),
                item.getProdutoId()
        ));

        return pedidoModel;
    }
}
