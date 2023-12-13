package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestauranteResumoModel extends RepresentationModel<RestauranteResumoModel> {
    private Long id;
    private String nome;
}
