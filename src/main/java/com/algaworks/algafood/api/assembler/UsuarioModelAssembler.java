package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class UsuarioModelAssembler {

    private final ModelMapper modelMapper;

    public UsuarioModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public Collection<UsuarioModel> toCollectionModel(Collection<Usuario> usuarios) {
        return usuarios.stream().map(this::toModel).collect(Collectors.toSet());
    }
}
