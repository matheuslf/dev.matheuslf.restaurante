package com.example.restaurante.dto;

public record PagamentoResponse(
        String status,
        String codigoTransacao
) {}
