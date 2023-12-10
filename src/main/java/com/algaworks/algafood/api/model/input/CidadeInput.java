package com.algaworks.algafood.api.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {

    @Schema(example = "Uberlândia")
    @NotBlank
    private String nome;

    @Schema(example = "1")
    @NotNull
    @Valid
    private EstadoIdInput estado;
}
