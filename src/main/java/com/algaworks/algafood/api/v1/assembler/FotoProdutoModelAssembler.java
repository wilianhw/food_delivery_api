package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.RestauranteFotoProdutoController;
import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public FotoProdutoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(RestauranteFotoProdutoController.class, FotoProdutoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        FotoProdutoModel fotoProdutoModel = createModelWithId(fotoProduto.getId(), fotoProduto, fotoProduto.getProduto().getId(), fotoProduto.getProduto().getRestaurante().getId());
        modelMapper.map(fotoProduto, fotoProdutoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            fotoProdutoModel.add(algaLinks.linkToProduto(
                    fotoProduto.getProduto().getRestaurante().getId(),
                    fotoProduto.getProduto().getId(),
                    "produto"));
        }

        return fotoProdutoModel;
    }
}
