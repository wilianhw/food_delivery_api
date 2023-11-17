package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    public RestauranteFormaPagamentoController(CadastroRestauranteService cadastroRestauranteService, FormaPagamentoModelAssembler formaPagamentoModelAssembler) {
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
    }

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociar(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associar(restauranteId, formaPagamentoId);
    }
}
