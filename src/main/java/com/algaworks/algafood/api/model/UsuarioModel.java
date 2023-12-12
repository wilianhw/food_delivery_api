package com.algaworks.algafood.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Data
@EqualsAndHashCode(callSuper = false)
public class UsuarioModel extends RepresentationModel<UsuarioModel> {
    private Long id;
    private String nome;
    private String email;
}
