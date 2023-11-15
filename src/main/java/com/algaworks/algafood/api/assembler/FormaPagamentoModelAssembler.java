package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class FormaPagamentoModelAssembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
    }

    public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasPagamento) {
        return formasPagamento.stream().map(this::toModel).toList();
    }
}
