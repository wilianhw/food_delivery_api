package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cidade;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public CidadeModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(CidadeController.class, CidadeModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public CidadeModel toModel(Cidade cidade) {
        CidadeModel cidadeModel = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeModel);

        if (algaSecurity.podeConsultarCidades()) {
            cidadeModel.add(algaLinks.linkToCidades("cidades"));
        }

        if (algaSecurity.podeConsultarEstados()) {
            cidadeModel.add(algaLinks.linkToEstado(cidadeModel.getEstado().getId()));
        }

        return cidadeModel;
    }

    @Override
    public CollectionModel<CidadeModel> toCollectionModel(Iterable<? extends Cidade> entities) {
        CollectionModel<CidadeModel> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            collectionModel.add(algaLinks.linkToCidades("cidades"));
        }

        return collectionModel;
    }
}
