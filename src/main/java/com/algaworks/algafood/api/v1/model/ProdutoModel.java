package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProdutoModel extends RepresentationModel<ProdutoModel> {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;
}
