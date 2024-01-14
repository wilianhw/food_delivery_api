package com.algaworks.algafood.api.v1.controller.openapi.controller;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "security_auth")
public interface EstatisticasControllerOpenApi {
    public static class EstatisticaModel extends RepresentationModel<EstatisticasController.EstatisticaModel> {
    }

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);

    EstatisticaModel estatisticas();
}
