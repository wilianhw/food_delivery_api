package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {
    @NotNull
    private Long produtoId;
    @NotNull
    private Long quantidade;
    private String observacao;
}
