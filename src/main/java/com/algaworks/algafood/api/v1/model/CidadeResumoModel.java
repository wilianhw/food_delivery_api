package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class CidadeResumoModel extends RepresentationModel<CidadeResumoModel> {
    private Long id;
    private String nome;
    private String estado;
}
