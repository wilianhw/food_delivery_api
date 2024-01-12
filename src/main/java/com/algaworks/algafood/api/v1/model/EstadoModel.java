package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "estados")
@Data
@EqualsAndHashCode(callSuper = false)
public class EstadoModel extends RepresentationModel<EstadoModel> {

    private Long id;

    private String nome;
}
