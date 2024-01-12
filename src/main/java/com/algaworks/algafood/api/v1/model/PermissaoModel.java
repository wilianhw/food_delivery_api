package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissaoModel extends RepresentationModel<PermissaoModel> {
    private Long id;
    private String nome;
    private String descricao;
}
