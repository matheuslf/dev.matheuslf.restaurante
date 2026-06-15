package com.example.restaurante.domain;

import com.example.restaurante.domain.enums.StatusPedido;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_abertura")
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.ABERTO;

    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mesa_id")
    private Mesa mesa;

    @PrePersist
    public void prePersist() {
        dataAbertura = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public StatusPedido getStatus() { return status; }
    public String getObservacao() { return observacao; }
    public Mesa getMesa() { return mesa; }

    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }
    public void setStatus(StatusPedido status) { this.status = status; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
}
