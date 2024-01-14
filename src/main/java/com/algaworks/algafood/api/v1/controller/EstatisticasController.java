package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping("/v1/estatisticas")
public class EstatisticasController implements com.algaworks.algafood.api.v1.controller.openapi.controller.EstatisticasControllerOpenApi {

    private final VendaQueryService vendaQueryService;
    private final AlgaLinks algaLinks;

    public EstatisticasController(VendaQueryService vendaQueryService, AlgaLinks algaLinks) {
        this.vendaQueryService = vendaQueryService;
        this.algaLinks = algaLinks;
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        return vendaQueryService.consultarVendasDiarias(filtro);
    }

    @CheckSecurity.Estatisticas.PodeConsultar
    @GetMapping
    public EstatisticaModel estatisticas() {
        EstatisticaModel estatisticaModel = new EstatisticaModel();

        estatisticaModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticaModel;
    }

}
