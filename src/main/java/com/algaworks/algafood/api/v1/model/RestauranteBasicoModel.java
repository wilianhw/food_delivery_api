package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Relation(collectionRelation = "restaurantes")
@Data
@EqualsAndHashCode(callSuper = false)
public class RestauranteBasicoModel extends RepresentationModel<RestauranteBasicoModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Italiano")
    private String nome;

    @Schema(example = "13.23")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

}
