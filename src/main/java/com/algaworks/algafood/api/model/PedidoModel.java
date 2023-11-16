package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModel {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private Endereco endereco;
    private StatusPedido status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataConfirmacao;
    private LocalDateTime dataCancelamento;
    private LocalDateTime dataEntrega;
    private UsuarioModel cliente;
    private RestauranteResumoModel restaurante;
    private FormaPagamentoModel formaPagamento;
    private List<ItemPedidoModel> itens;
}
