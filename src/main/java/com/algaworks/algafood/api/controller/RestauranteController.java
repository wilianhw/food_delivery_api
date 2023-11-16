package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final RestauranteModelAssembler restauranteModelAssembler;
    private final RestauranteInputDisassembler restauranteInputDisassembler;

    public RestauranteController(RestauranteRepository restauranteRepository, CadastroRestauranteService cadastroRestauranteService, RestauranteModelAssembler restauranteModelAssembler, RestauranteInputDisassembler restauranteInputDisassembler) {
        this.restauranteRepository = restauranteRepository;
        this.cadastroRestauranteService = cadastroRestauranteService;
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
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel cadastrar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(
            @PathVariable Long restauranteId,
            @RequestBody @Valid RestauranteInput restauranteInput
    ) {
        Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

        try {
            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.apagar(restauranteId);
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrir(@PathVariable Long restauranteId) {
        cadastroRestauranteService.abrir(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fechar(@PathVariable Long restauranteId) {
        cadastroRestauranteService.fechar(restauranteId);
    }
}
