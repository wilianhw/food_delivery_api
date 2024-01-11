package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public RestauranteModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(RestauranteController.class, RestauranteModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public RestauranteModel toModel(Restaurante restaurante) {
        RestauranteModel restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.add(algaLinks.linkToRestaurnateFormasPagamento(
                restauranteModel.getId(),
                "formas-pagamento"
        ));

        restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId()));

        restauranteModel.getCozinha().add(algaLinks.linkToCozinhas(restauranteModel.getCozinha().getId()));

        restauranteModel.getEndereco().getCidade().add(algaLinks.linkToCidades(
                restauranteModel.getEndereco().getCidade().getId()
        ));

        if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
            if (restaurante.podeInativar()) {
                restauranteModel.add(algaLinks.linkToInativarRestaurante(restauranteModel.getId(), "inativar"));
            }

            if (restaurante.podeAtivar()) {
                restauranteModel.add(algaLinks.linkToAtivarRestaurante(restauranteModel.getId(), "ativar"));
            }
        }

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
            if (restaurante.podeAbrir()) {
                restauranteModel.add(algaLinks.linkToAbrirRestaurante(restauranteModel.getId(), "abrir"));
            }

            if (restaurante.podeFechar()) {
                restauranteModel.add(algaLinks.linkToFecharRestaurante(restauranteModel.getId(), "fechar"));
            }
        }

        return restauranteModel;
    }
}
