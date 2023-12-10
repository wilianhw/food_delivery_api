package com.algaworks.algafood.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeModel {

    @Schema(example = "1")
    private Long id;
    @Schema(example = "Uberlândia")
    private String nome;
    private EstadoModel estado;
}
