package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestaurante;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
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
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return cadastroRestaurante.buscarOuFalhar(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante cadastrar(@RequestBody @Valid Restaurante restaurante) {
        try {
            return cadastroRestaurante.salvar(restaurante);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(
            @PathVariable Long restauranteId,
            @RequestBody Restaurante restaurante
    ) {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "restaurante", "produtos");

        try {
            return cadastroRestaurante.salvar(restauranteAtual);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long restauranteId) {
        cadastroRestaurante.apagar(restauranteId);
    }
}
