package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProduto;
import com.algaworks.algafood.domain.service.CadastroRestaurante;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final ProdutoModelAssembler produtoModelAssembler;
    private final ProdutoInputDisassembler produtoInputDisassembler;
    private final CadastroRestaurante cadastroRestaurante;
    private final CadastroProduto cadastroProduto;
    private final ProdutoRepository produtoRepository;

    public RestauranteProdutoController(CadastroRestaurante cadastroRestaurante, ProdutoModelAssembler produtoModelAssembler, ProdutoInputDisassembler produtoInputDisassembler, CadastroProduto cadastroProduto, ProdutoRepository produtoRepository) {
        this.cadastroRestaurante = cadastroRestaurante;
        this.produtoModelAssembler = produtoModelAssembler;
        this.produtoInputDisassembler = produtoInputDisassembler;
        this.cadastroProduto = cadastroProduto;
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public Set<ProdutoModel> listar(@PathVariable Long restauranteId) {
        return produtoModelAssembler.toCollectionModel(produtoRepository.findByRestauranteId(restauranteId));
    }

    @GetMapping("/{produtoId}")
    public ProdutoModel buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        return produtoModelAssembler.toModel(cadastroProduto.buscarOuFalhar(restauranteId, produtoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel cadastrar(@PathVariable Long restauranteId, @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoModelAssembler.toModel(cadastroProduto.salvar(produto));
    }

    @PutMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModel atualizar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual.setRestaurante(restaurante);

        return produtoModelAssembler.toModel(cadastroProduto.salvar(produtoAtual));
    }
}
