package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }
}
