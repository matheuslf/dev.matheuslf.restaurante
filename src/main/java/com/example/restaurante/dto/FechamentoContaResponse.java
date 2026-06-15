package com.example.restaurante.dto;

import com.example.restaurante.domain.FechamentoConta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FechamentoContaResponse(
        Long id,
        Long pedidoId,
        Integer numeroMesa,
        BigDecimal subtotal,
        BigDecimal taxaServico,
        BigDecimal desconto,
        BigDecimal total,
        LocalDateTime dataFechamento
) {

    public static FechamentoContaResponse fromEntity(FechamentoConta fechamento) {
        return new FechamentoContaResponse(
                fechamento.getId(),
                fechamento.getPedido().getId(),
                fechamento.getPedido().getMesa().getNumero(),
                fechamento.getSubtotal(),
                fechamento.getTaxaServico(),
                fechamento.getDesconto(),
                fechamento.getTotal(),
                fechamento.getDataFechamento()
        );
    }
}