package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    private final ProdutoRepository produtoRepository;

    public CatalogoFotoProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Long produtoId = fotoProduto.getProduto().getId();
        Optional<FotoProduto> fotoProdutoExistente = produtoRepository.findFotoById(restauranteId, produtoId);

        fotoProdutoExistente.ifPresent(produtoRepository::delete);

        return produtoRepository.save(fotoProduto);
    }
}
