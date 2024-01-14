package com.algaworks.algafood.api.v1.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;

@RestController
@RequestMapping(path = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController implements com.algaworks.algafood.api.v1.controller.openapi.controller.RootEntryPointControllerOpenApi {

    private final AlgaLinks algaLinks;

    public RootEntryPointController(AlgaLinks algaLinks) {
        this.algaLinks = algaLinks;
    }

    @GetMapping
    public RootEntryPointModel root() {
        RootEntryPointModel rootEntryPointModel = new RootEntryPointModel();

        rootEntryPointModel.add(algaLinks.linkToCidades("cidades"));
        rootEntryPointModel.add(algaLinks.linkToCozinhas("cozinhas"));
        rootEntryPointModel.add(algaLinks.linkToEstado("estados"));
        rootEntryPointModel.add(algaLinks.linkToFormaPagamento("formas-pagamento"));
        rootEntryPointModel.add(algaLinks.linkToGrupos("grupos"));
        rootEntryPointModel.add(algaLinks.linkToPedidos("pedidos"));
        rootEntryPointModel.add(algaLinks.linkToPermissoes("permissoes"));
        rootEntryPointModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        rootEntryPointModel.add(algaLinks.linkToUsuarios("usuarios"));
        rootEntryPointModel.add(algaLinks.linkToEstatisticas("estatistica"));

        return rootEntryPointModel;
    }

}
