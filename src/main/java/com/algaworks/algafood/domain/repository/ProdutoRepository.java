package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Produto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends CustomJpaRepository<Produto, Long> {
    @Query("from Produto where id = :produto and restaurante.id = :restaurante")
    Optional<Produto> findByIdAndRestauranteId(@Param("produto") Long produtoId, @Param("restaurante") Long restauranteId);

    List<Produto> findByRestauranteId(Long restauranteId);
}
