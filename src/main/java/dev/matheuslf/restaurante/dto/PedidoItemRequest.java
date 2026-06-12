package dev.matheuslf.restaurante.dto;

public record PedidoItemRequest(
        Long produtoId,
        Integer quantidade,
        String observacao
) {
}
