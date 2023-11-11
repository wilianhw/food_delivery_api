package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
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
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final RestauranteInputDisassembler restauranteInputDisassembler;

    public RestauranteController(RestauranteRepository restauranteRepository, CadastroRestaurante cadastroRestaurante, RestauranteModelAssembler restauranteModelAssembler, RestauranteInputDisassembler restauranteInputDisassembler) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroRestaurante = cadastroRestaurante;
        this.restauranteModelAssembler = restauranteModelAssembler;
        this.restauranteInputDisassembler = restauranteInputDisassembler;
    }

    @GetMapping
    public List<RestauranteModel> list() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
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
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel cadastrar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(
            @PathVariable Long restauranteId,
            @RequestBody RestauranteInput restauranteInput
    ) {
        Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

        BeanUtils.copyProperties(restauranteInputDisassembler.toDomainObject(restauranteInput), restauranteAtual, "id", "formasPagamento", "restaurante", "produtos");

        try {
            return restauranteModelAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
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
