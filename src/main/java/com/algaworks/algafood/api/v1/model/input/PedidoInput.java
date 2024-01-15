package com.algaworks.algafood.api.v1.model.input;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class PedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;
    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;
    @Valid
    @NotNull
    private EnderecoInput enderecoEntrega;
    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInput> itens;
}
