package com.algaworks.algafood.api.v1.disassembler;

import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoInputDisassembler {

    private final ModelMapper modelMapper;

    public PedidoInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Pedido toDomainObject(PedidoInput pedidoInput) {
        return modelMapper.map(pedidoInput, Pedido.class);
    }
}
