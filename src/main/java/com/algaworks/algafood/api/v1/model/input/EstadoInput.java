package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoInput {

    @Schema(example = "Nome do estado")
    @NotBlank
    private String nome;
}
