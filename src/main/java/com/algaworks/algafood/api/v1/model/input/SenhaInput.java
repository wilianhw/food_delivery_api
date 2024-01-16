package com.algaworks.algafood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaInput {

    @Schema(example = "senhaAtual123", type = "string")
    @NotBlank
    private String senhaAtual;

    @Schema(example = "novaSenha123", type = "string")
    @NotBlank
    private String novaSenha;
}
