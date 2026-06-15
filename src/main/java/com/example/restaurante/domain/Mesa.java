package com.example.restaurante.domain;

import com.example.restaurante.domain.enums.StatusMesa;
import jakarta.persistence.*;

@Entity
@Table(name = "mesas")
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private String descricao;
    private Integer capacidade;

    @Enumerated(EnumType.STRING)
    private StatusMesa status = StatusMesa.LIVRE;

    public Long getId() { return id; }
    public Integer getNumero() { return numero; }
    public String getDescricao() { return descricao; }
    public Integer getCapacidade() { return capacidade; }
    public StatusMesa getStatus() { return status; }

    public void setNumero(Integer numero) { this.numero = numero; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public void setStatus(StatusMesa status) { this.status = status; }
}
