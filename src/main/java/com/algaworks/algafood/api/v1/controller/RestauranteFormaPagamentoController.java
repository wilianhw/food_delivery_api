package com.algaworks.algafood.api.v1.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private final CadastroRestauranteService cadastroRestauranteService;
    private final FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    private final AlgaLinks algaLinks;

    public RestauranteFormaPagamentoController(CadastroRestauranteService cadastroRestauranteService, FormaPagamentoModelAssembler formaPagamentoModelAssembler, AlgaLinks algaLinks) {
        this.cadastroRestauranteService = cadastroRestauranteService;
        this.formaPagamentoModelAssembler = formaPagamentoModelAssembler;
        this.algaLinks = algaLinks;
    }

    @CheckSecurity.Restaurantes.PodeConsultarRestaurantes
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

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.desassociar(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        cadastroRestauranteService.associar(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }
}
