package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    private final VendaQueryService vendaQueryService;
    private final AlgaLinks algaLinks;

    public EstatisticasController(VendaQueryService vendaQueryService, AlgaLinks algaLinks) {
        this.vendaQueryService = vendaQueryService;
        this.algaLinks = algaLinks;
    }

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }

    @GetMapping
    public EstatisticaModel estatisticas() {
        EstatisticaModel estatisticaModel = new EstatisticaModel();

        estatisticaModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticaModel;
    }

    public static class EstatisticaModel extends RepresentationModel<EstatisticaModel> {
    }
}
