package com.algaworks.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.swagger.v3.oas.annotations.media.Schema;

@Relation(collectionRelation = "grupos")
@Data
@EqualsAndHashCode(callSuper = false)
public class GrupoModel extends RepresentationModel<GrupoModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Gerente")
    private String nome;
}
