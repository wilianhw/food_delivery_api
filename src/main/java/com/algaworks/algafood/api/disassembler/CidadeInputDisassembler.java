package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CidadeInputDisassembler {

    private final ModelMapper modelMapper;

    public CidadeInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }

    public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
        modelMapper.map(cidadeInput, cidade);
    }
}
