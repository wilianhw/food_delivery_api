package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler {

    private final ModelMapper modelMapper;

    public FotoProdutoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoModel.class);
    }
}
