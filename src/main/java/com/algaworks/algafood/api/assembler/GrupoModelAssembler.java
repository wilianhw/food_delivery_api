package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GrupoModelAssembler {

    private final ModelMapper modelMapper;

    public GrupoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GrupoModel toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoModel.class);
    }

    public Collection<GrupoModel> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream().map(this::toModel).toList();
    }
}
