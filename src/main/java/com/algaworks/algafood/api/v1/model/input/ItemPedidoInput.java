package com.algaworks.algafood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class ItemPedidoInput {

    @Schema(example = "1")
    @NotNull
    private Long produtoId;

    @Schema(example = "10")
    @NotNull
    private Long quantidade;

    @Schema(example = "Entrega rápida")
    private String observacao;
}
