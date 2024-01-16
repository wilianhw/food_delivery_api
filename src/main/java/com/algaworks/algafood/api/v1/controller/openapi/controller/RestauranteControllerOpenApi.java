package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenApi {
    CollectionModel<RestauranteBasicoModel> list();

    Restaurante consultarPorNome(String nome, Long cozinhaId);

    Restaurante consultarPorQueryNomeada(String nome, Long cozinhaId);

    List<Restaurante> consultarPorNomeTaxaFrete(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    );

    List<Restaurante> consultarPorNomeTaxaFreteCriteria(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    );

    List<Restaurante> consultarPorNomeTaxaFreteSDJ(String nome);

    RestauranteModel buscar(Long restauranteId);

    RestauranteModel cadastrar(RestauranteInput restauranteInput);

    RestauranteModel atualizar(
            Long restauranteId,
            RestauranteInput restauranteInput
    );

    void apagar(Long restauranteId);

    ResponseEntity<Void> ativar(Long restauranteId);

    void ativarMultiplos(List<Long> restauranteIds);

    void inativarMultiplos(List<Long> restauranteIds);

    ResponseEntity<Void> inativar(Long restauranteId);

    ResponseEntity<Void> abrir(Long restauranteId);

    ResponseEntity<Void> fechar(Long restauranteId);
}
