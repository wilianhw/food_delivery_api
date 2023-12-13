package com.algaworks.algafood.api.model;

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
