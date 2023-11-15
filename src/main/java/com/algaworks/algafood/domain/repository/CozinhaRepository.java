package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
}
