package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestaurante;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final CadastroRestaurante cadastroRestaurante;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    public RestauranteFormaPagamentoController(CadastroRestaurante cadastroRestaurante, FormaPagamentoModelAssembler formaPagamentoModelAssembler) {
        this.cadastroRestaurante = cadastroRestaurante;
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
    }

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.desassociar(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestaurante.associar(restauranteId, formaPagamentoId);
    }
}
