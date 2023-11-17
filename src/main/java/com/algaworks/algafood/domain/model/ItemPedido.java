package com.algaworks.algafood.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal precoTotal;

    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    public void calcularPrecoTotal() {
        BigDecimal pUnitario = this.getPrecoUnitario();
        Integer qts = this.getQuantidade();

        if (pUnitario == null) {
            pUnitario = BigDecimal.ZERO;
        }

        if (qts == null) {
            qts = 0;
        }

        this.setPrecoTotal(pUnitario.multiply(new BigDecimal(qts)));
    }
}
