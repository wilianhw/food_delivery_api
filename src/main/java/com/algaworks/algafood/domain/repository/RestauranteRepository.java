package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    Restaurante consultarPorNome(String nome, @Param("id") Long cozinhaId);

    Restaurante consultarPorQueryNomeada(String nome, @Param("id") Long cozinhaId);
}
