package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Restaurante;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

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

    RestauranteModel buscar(@PathVariable Long restauranteId);

    RestauranteModel cadastrar(@RequestBody @Valid RestauranteInput restauranteInput);

    RestauranteModel atualizar(
            @PathVariable Long restauranteId,
            @RequestBody @Valid RestauranteInput restauranteInput
    );

    void apagar(@PathVariable Long restauranteId);

    ResponseEntity<Void> ativar(@PathVariable Long restauranteId);

    void ativarMultiplos(@RequestBody List<Long> restauranteIds);

    void inativarMultiplos(@RequestBody List<Long> restauranteIds);

    ResponseEntity<Void> inativar(@PathVariable Long restauranteId);

    ResponseEntity<Void> abrir(@PathVariable Long restauranteId);

    ResponseEntity<Void> fechar(@PathVariable Long restauranteId);
}
