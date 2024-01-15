package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInput {

    @Schema(example = "11111-100")
    @NotBlank
    private String cep;

    @Schema(example = "Joaquim Barbosa")
    @NotBlank
    private String logradouro;

    @Schema(example = "13")
    @NotBlank
    private String numero;

    @Schema(example = "Centro")
    @NotBlank
    private String bairro;

    @Schema(example = "Casa")
    private String complemento;

    @Valid
    @NotNull
    private CidadeIdInput cidade;
}
