package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public PermissaoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(PermissaoController.class, PermissaoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = createModelWithId(permissao.getId(), permissao);
        modelMapper.map(permissao, permissaoModel);

        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes("permissoes"));
    }
}
