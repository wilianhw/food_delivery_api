package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Chileno")
    private String nome;
}
