package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @Schema(example = "137kfaç1234j-fklsdj")
    private String codigo;

    @Schema(example = "14.30")
    private BigDecimal subtotal;

    @Schema(example = "3.0")
    private BigDecimal taxaFrete;

    @Schema(example = "20.50")
    private BigDecimal valorTotal;

    @Schema(example = "Rua Joaquim Souza")
    private EnderecoModel endereco;

    @Schema(example = "CRIADO")
    private StatusPedido status;

    @Schema(example = "2022-12-01T20:34:04Z")
    private LocalDateTime dataCriacao;

    @Schema(example = "2022-12-01T20:34:04Z")
    private LocalDateTime dataConfirmacao;

    @Schema(example = "2022-12-01T20:34:04Z")
    private LocalDateTime dataCancelamento;

    @Schema(example = "2022-12-01T20:34:04Z")
    private LocalDateTime dataEntrega;

    private UsuarioModel cliente;

    private RestauranteResumoModel restaurante;

    private FormaPagamentoModel formaPagamento;

    private List<ItemPedidoModel> itens;
}
