package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Data
@EqualsAndHashCode(callSuper = false)
public class CozinhaModel extends RepresentationModel<CozinhaModel> {
    private Long id;
    private String nome;
}
