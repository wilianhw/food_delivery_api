package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoModelAssembler {

    private final ModelMapper modelMapper;

    public PedidoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoModel.class);
    }

    public List<PedidoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toModel).toList();
    }
}
