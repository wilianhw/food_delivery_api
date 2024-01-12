package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaModelAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public CozinhaModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(CozinhaController.class, CozinhaModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public CozinhaModel toModel(Cozinha cozinha) {
        CozinhaModel cozinhaModel = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModel);

        if (algaSecurity.podeConsultarCozinhas()) {
            cozinhaModel.add(algaLinks.linkToCozinhas("cozinhas"));
        }

        return cozinhaModel;
    }
}
