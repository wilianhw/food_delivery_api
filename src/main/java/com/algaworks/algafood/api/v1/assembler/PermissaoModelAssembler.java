package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Permissao;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public PermissaoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(PermissaoController.class, PermissaoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public PermissaoModel toModel(Permissao permissao) {
        PermissaoModel permissaoModel = createModelWithId(permissao.getId(), permissao);
        modelMapper.map(permissao, permissaoModel);

        return permissaoModel;
    }

    @Override
    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        CollectionModel<PermissaoModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            collectionModel.add(algaLinks.linkToPermissoes("permissoes"));
        }

        return collectionModel;
    }
}
