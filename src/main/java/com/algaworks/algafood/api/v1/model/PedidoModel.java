package com.algaworks.algafood.api.v1.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoModel extends RepresentationModel<PedidoModel> {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private EnderecoModel endereco;
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
