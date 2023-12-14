package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public RestauranteModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(RestauranteController.class, RestauranteModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurante("restaurantes"));

        restauranteModel.add(algaLinks.linkToRestaurnateFormasPagamento(
                restauranteModel.getId(),
                "formas-pagamento"
        ));

        restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId()));

        return restauranteModel;
    }
}
