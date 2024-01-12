package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class FotoProdutoModel extends RepresentationModel<FotoProdutoModel> {
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
