package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoResumoModelAssembler {

    private final ModelMapper modelMapper;

    public PedidoResumoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PedidoResumoModel toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoModel.class);
    }

    public List<PedidoResumoModel> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream().map(this::toModel).toList();
    }
}
