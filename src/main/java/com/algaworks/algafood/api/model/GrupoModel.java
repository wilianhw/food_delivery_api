package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "grupos")
@Data
@EqualsAndHashCode(callSuper = false)
public class GrupoModel extends RepresentationModel<GrupoModel> {
    private Long id;
    private String nome;
}
