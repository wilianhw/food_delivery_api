package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries {
    @Query("from Produto where id = :produto and restaurante.id = :restaurante")
    Optional<Produto> findByIdAndRestauranteId(@Param("produto") Long produtoId, @Param("restaurante") Long restauranteId);

    List<Produto> findByRestauranteId(Long restauranteId);

    @Query("select f from FotoProduto f join f.produto p " +
            "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
    Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
}
