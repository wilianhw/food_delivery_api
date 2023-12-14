package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public PedidoResumoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(PedidoController.class, PedidoResumoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        pedidoResumoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        pedidoResumoModel.getCliente().add(algaLinks.linkToUsuarios(pedido.getCliente().getId()));

        return pedidoResumoModel;
    }
}
