package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Permissao;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CustomJpaRepository<Permissao, Long> {
}
