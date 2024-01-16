package com.algaworks.algafood.api.v1.model.input;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ProdutoInput {

    @Schema(example = "Batata")
    @NotBlank
    private String nome;

    @Schema(example = "Fresca")
    @NotBlank
    private String descricao;

    @Schema(example = "21.30")
    @PositiveOrZero
    @NotNull
    private BigDecimal preco;

    @Schema(example = "true")
    @NotNull
    private Boolean ativo;
}
