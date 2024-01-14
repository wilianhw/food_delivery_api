package com.algaworks.algafood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.v1.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements com.algaworks.algafood.api.v1.controller.openapi.controller.RestauranteProdutoControllerOpenApi {

    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoInputDisassembler produtoInputDisassembler;
    private final CadastroRestauranteService cadastroRestauranteService;
    private final CadastroProdutoService cadastroProdutoService;
    private final ProdutoRepository produtoRepository;

    public RestauranteProdutoController(CadastroRestauranteService cadastroRestauranteService, ProdutoModelAssembler produtoModelAssembler, ProdutoInputDisassembler produtoInputDisassembler, CadastroProdutoService cadastroProdutoService, ProdutoRepository produtoRepository) {
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.produtoModelAssembler = produtoModelAssembler;
        this.produtoInputDisassembler = produtoInputDisassembler;
        this.cadastroProdutoService = cadastroProdutoService;
        this.produtoRepository = produtoRepository;
    }

    @CheckSecurity.Restaurantes.PodeConsultarRestaurantes
    @GetMapping
    public CollectionModel<ProdutoModel> listar(@PathVariable Long restauranteId) {
        return produtoModelAssembler.toCollectionModel(produtoRepository.findByRestauranteId(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultarRestaurantes
    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return produtoModelAssembler.toModel(cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel cadastrar(@PathVariable Long restauranteId, @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produto));
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel atualizar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produtoAtual = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual.setRestaurante(restaurante);

        return produtoModelAssembler.toModel(cadastroProdutoService.salvar(produtoAtual));
    }
}
