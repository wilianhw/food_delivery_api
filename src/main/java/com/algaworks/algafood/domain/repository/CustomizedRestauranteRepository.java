package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface CustomizedRestauranteRepository {
    List<Restaurante> findWithJPQL(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
    List<Restaurante> findWithCriteria(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
    List<Restaurante> findWithFreeFrete(String nome);
}
