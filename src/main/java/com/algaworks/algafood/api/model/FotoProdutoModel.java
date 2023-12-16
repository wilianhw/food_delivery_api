package com.algaworks.algafood.api.model;

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
