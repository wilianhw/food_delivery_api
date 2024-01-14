package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;

@Relation(collectionRelation = "cidades")
@Data
@EqualsAndHashCode(callSuper = false)
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Uberlândia")
    private String nome;

    private EstadoModel estado;
}
