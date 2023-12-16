package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.RestauranteFotoProdutoController;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoModelAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;

    public FotoProdutoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks) {
        super(RestauranteFotoProdutoController.class, FotoProdutoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
    }

    @Override
    public FotoProdutoModel toModel(FotoProduto fotoProduto) {
        FotoProdutoModel fotoProdutoModel = createModelWithId(fotoProduto.getId(), fotoProduto, fotoProduto.getProduto().getId(), fotoProduto.getProduto().getRestaurante().getId());
        modelMapper.map(fotoProduto, fotoProdutoModel);

        fotoProdutoModel.add(algaLinks.linkToProduto(
                fotoProduto.getProduto().getRestaurante().getId(),
                fotoProduto.getProduto().getId(),
                "produto"));

        return fotoProdutoModel;
    }
}
