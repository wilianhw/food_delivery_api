package com.algaworks.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@EqualsAndHashCode(callSuper = false)
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {

    @Schema(example = "1")
    private Long produtoId;

    @Schema(example = "Batata")
    private String produtoNome;

    @Schema(example = "10")
    private Integer quantidade;

    @Schema(example = "14.17")
    private BigDecimal precoUnitario;

    @Schema(example = "23.11")
    private BigDecimal precoTotal;

    @Schema(example = "Fresca")
    private String observacao;
}
