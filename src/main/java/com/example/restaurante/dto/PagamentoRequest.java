package com.example.restaurante.dto;

public record PagamentoRequest(
        Double valor,
        String formaPagamento
) {}
