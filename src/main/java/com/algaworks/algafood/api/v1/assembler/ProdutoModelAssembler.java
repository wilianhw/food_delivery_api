package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    private final ModelMapper modelMapper;
    private final AlgaLinks algaLinks;
    private final AlgaSecurity algaSecurity;

    public ProdutoModelAssembler(ModelMapper modelMapper, AlgaLinks algaLinks, AlgaSecurity algaSecurity) {
        super(RestauranteProdutoController.class, ProdutoModel.class);
        this.modelMapper = modelMapper;
        this.algaLinks = algaLinks;
        this.algaSecurity = algaSecurity;
    }


    public ProdutoModel toModel(Produto produto) {
        ProdutoModel produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
        modelMapper.map(produto, produtoModel);

        if (algaSecurity.podeConsultarRestaurantes()) {
            produtoModel.add(algaLinks.linkToProduto(produto.getRestaurante().getId(), produto.getId()));

            produtoModel.add(algaLinks.linkToFotoProduto(produto.getRestaurante().getId(), produto.getId(), "foto"));
        }

        return produtoModel;
    }
}
