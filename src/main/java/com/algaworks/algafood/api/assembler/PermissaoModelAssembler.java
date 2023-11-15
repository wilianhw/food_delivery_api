package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler {

    private final ModelMapper modelMapper;

    public PermissaoModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public Set<PermissaoModel> toCollectionModel(Collection<Permissao> permissoes) {
        return permissoes.stream().map(this::toModel).collect(Collectors.toSet());
    }
}
