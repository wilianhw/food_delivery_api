package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {
    private Long id;
    private String descricao;
}
