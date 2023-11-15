package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioModelAssembler {

    private final ModelMapper modelMapper;

    public UsuarioModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UsuarioModel toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioModel.class);
    }

    public List<UsuarioModel> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream().map(this::toModel).toList();
    }
}
