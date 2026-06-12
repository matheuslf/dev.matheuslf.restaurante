package dev.matheuslf.restaurante.dto;

import java.math.BigDecimal;

public record FechamentoContaRequest(
        BigDecimal taxaServico,
        BigDecimal desconto
) {
}
