package com.example.restaurante.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fechamentos_conta")
public class FechamentoConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal subtotal;

    @Column(name = "taxa_servico")
    private BigDecimal taxaServico;

    private BigDecimal desconto;
    private BigDecimal total;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @PrePersist
    public void prePersist() {
        dataFechamento = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTaxaServico() { return taxaServico; }
    public BigDecimal getDesconto() { return desconto; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public Pedido getPedido() { return pedido; }

    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    public void setTaxaServico(BigDecimal taxaServico) { this.taxaServico = taxaServico; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}
