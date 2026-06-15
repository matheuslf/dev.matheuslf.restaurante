package com.example.restaurante.domain;

import com.example.restaurante.domain.enums.FormaPagamento;
import com.example.restaurante.domain.enums.StatusPagamento;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status = StatusPagamento.PENDENTE;

    @Column(name = "codigo_transacao_externa")
    private String codigoTransacaoExterna;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public BigDecimal getValor() { return valor; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public StatusPagamento getStatus() { return status; }
    public String getCodigoTransacaoExterna() { return codigoTransacaoExterna; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public Pedido getPedido() { return pedido; }

    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    public void setStatus(StatusPagamento status) { this.status = status; }
    public void setCodigoTransacaoExterna(String codigoTransacaoExterna) { this.codigoTransacaoExterna = codigoTransacaoExterna; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}
