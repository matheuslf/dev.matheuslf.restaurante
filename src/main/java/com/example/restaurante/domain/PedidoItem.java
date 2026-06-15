package com.example.restaurante.domain;

import com.example.restaurante.domain.enums.StatusItemPedido;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido_itens")
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidade;

    @Column(name = "preco_unitario")
    private BigDecimal precoUnitario;

    private String observacao;

    @Enumerated(EnumType.STRING)
    private StatusItemPedido status = StatusItemPedido.PENDENTE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Column(name = "data_inicio_preparo")
    private LocalDateTime dataInicioPreparo;

    @Column(name = "data_pronto")
    private LocalDateTime dataPronto;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    public Long getId() { return id; }
    public Integer getQuantidade() { return quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public String getObservacao() { return observacao; }
    public StatusItemPedido getStatus() { return status; }
    public Pedido getPedido() { return pedido; }
    public Produto getProduto() { return produto; }

    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public void setStatus(StatusItemPedido status) { this.status = status; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public LocalDateTime getDataInicioPreparo() {
        return dataInicioPreparo;
    }

    public void setDataInicioPreparo(LocalDateTime dataInicioPreparo) {
        this.dataInicioPreparo = dataInicioPreparo;
    }

    public LocalDateTime getDataPronto() {
        return dataPronto;
    }

    public void setDataPronto(LocalDateTime dataPronto) {
        this.dataPronto = dataPronto;
    }

    public LocalDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
