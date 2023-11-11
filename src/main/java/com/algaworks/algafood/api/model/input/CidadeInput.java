package com.algaworks.algafood.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {

    @NotBlank
    private String nome;

    @NotNull
    @Valid
    private EstadoIdInput estado;
}
