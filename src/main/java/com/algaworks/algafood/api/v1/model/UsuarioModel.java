package com.algaworks.algafood.api.v1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;

@Relation(collectionRelation = "usuarios")
@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "João")
    private String nome;

    @Schema(example = "joao@dev.com")
    private String email;
}
