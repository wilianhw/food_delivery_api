package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestaurante;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestaurante cadastroRestaurante;

    public RestauranteController(RestauranteRepository restauranteRepository, CadastroRestaurante cadastroRestaurante) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroRestaurante = cadastroRestaurante;
    }

    @GetMapping
    public List<Restaurante> list() {
        return restauranteRepository.findAll();
    }

    @GetMapping("/por-nome")
    public Restaurante consultarPorNome(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorNome(nome, cozinhaId);
    }

    @GetMapping("/por-query-nomeada")
    public Restaurante consultarPorQueryNomeada(String nome, Long cozinhaId) {
        return restauranteRepository.consultarPorQueryNomeada(nome, cozinhaId);
    }

    @GetMapping("/por-nome-e-frete")
    public List<Restaurante> consultarPorNomeTaxaFrete(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    ) {
        return restauranteRepository.findWithJPQL(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/por-nome-e-frete-criteria")
    public List<Restaurante> consultarPorNomeTaxaFreteCriteria(
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    ) {
        return restauranteRepository.findWithCriteria(nome, taxaFreteInicial, taxaFreteFinal);
    }

    @GetMapping("/por-nome-e-frete-sdj")
    public List<Restaurante> consultarPorNomeTaxaFreteSDJ(String nome) {
        return restauranteRepository.findWithFreeFrete(nome);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscarOuFalhar(@PathVariable Long restauranteId) {
        try {
            Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

            return ResponseEntity.ok(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestaurante.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long restauranteId,
            @RequestBody Restaurante restaurante
    ) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento");

            cadastroRestaurante.salvar(restauranteAtual);

            return ResponseEntity.ok(restauranteAtual);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
