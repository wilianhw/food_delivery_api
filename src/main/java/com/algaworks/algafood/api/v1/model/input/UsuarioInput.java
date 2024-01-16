package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioInput {

    @Schema(example = "Pedro")
    @NotBlank
    private String nome;

    @Schema(example = "pedro@dev.com")
    @Email
    @NotBlank
    private String email;
}
