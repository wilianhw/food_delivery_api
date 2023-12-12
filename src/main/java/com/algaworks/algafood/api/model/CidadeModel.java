package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

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
