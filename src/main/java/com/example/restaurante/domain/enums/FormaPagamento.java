package com.example.restaurante.domain.enums;

public enum FormaPagamento {
    DINHEIRO,
    CARTAO_CREDITO,
    CARTAO_DEBITO,
    PIX;

    public static FormaPagamento getFormaPagamento(String formaPagamento) {
        if (formaPagamento == null) {
            throw new IllegalArgumentException("Forma de pagamento não pode ser nula");
        }

        try {
            return FormaPagamento.valueOf(formaPagamento.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Forma de pagamento inválida: " + formaPagamento);
        }
    }
}
