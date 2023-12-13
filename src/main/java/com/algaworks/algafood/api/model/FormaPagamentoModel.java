package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class FormaPagamentoModel extends RepresentationModel<FormaPagamentoModel> {
    private Long id;
    private String descricao;
}
