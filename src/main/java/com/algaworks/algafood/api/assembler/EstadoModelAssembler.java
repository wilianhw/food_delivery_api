package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.model.EstadoModel;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public EstadoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(EstadoController.class, EstadoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    public EstadoModel toModel(Estado estado) {
        EstadoModel estadoModel = createModelWithId(estado.getId(), estado);

        modelMapper.map(estado, estadoModel);

        estadoModel.add(algaLinks.linkToEstados());

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToEstados());
    }
}
