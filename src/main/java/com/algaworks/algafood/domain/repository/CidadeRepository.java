package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cidade;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends CustomJpaRepository<Cidade, Long> {
}
