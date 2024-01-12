package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestauranteBasicoModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public RestauranteBasicoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(RestauranteController.class, RestauranteBasicoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }


    @Override
    public RestauranteBasicoModel toModel(Restaurante restaurante) {
        RestauranteBasicoModel restauranteBasicoModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteBasicoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            restauranteBasicoModel.add(
                    algaLinks.linkToRestaurantes("restaurantes"));
        }

        if (algaSecurity.podeConsultarCozinhas()) {
            restauranteBasicoModel.getCozinha().add(
                    algaLinks.linkToCozinhas(restauranteBasicoModel.getCozinha().getId()));
        }

        return restauranteBasicoModel;
    }
}
