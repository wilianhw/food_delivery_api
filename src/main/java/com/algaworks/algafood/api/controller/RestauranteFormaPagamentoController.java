package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private final AlgaLinks algaLinks;

    public RestauranteFormaPagamentoController(CadastroRestauranteService cadastroRestauranteService, FormaPagamentoModelAssembler formaPagamentoModelAssembler, AlgaLinks algaLinks) {
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
        this.algaLinks = algaLinks;
    }

    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(algaLinks.linkToFormaPagamento("formas-pagamento"))
                .add(algaLinks.linkToRestaurnateFormasPagamentoAssociacao(restauranteId, "associar"));

        formasPagamentoModel.getContent().forEach(formaPagamentoModel -> formaPagamentoModel.add(algaLinks.linkToRestaurnateFormasPagamentoDesassociacao(restauranteId, formaPagamentoModel.getId(), "desassociar")));

        return formasPagamentoModel;
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociar(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associar(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }
}
