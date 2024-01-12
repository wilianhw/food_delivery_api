package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Data
@EqualsAndHashCode(callSuper = false)
public class CidadeModel extends RepresentationModel<CidadeModel> {

    private Long id;
    private String nome;
    private EstadoModel estado;
}
