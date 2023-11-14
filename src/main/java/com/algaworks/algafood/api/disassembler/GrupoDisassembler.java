package com.algaworks.algafood.api.disassembler;

import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GrupoDisassembler {

    private final ModelMapper modelMapper;

    public GrupoDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }

    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }
}
