package dev.matheuslf.restaurante.dto;

import dev.matheuslf.restaurante.domain.entity.Pedido;
import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record PedidoItemResponse(
        Long id,
        Long pedidoId,
        Long produtoId,
        String produtoNome,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal total,
        String observacao,
        StatusItemPedido status
) {

    public static PedidoItemResponse fromEntity(PedidoItem item) {
        BigDecimal total = item.getPrecoUnitario()
                            .multiply(BigDecimal.valueOf(item.getQuantidade()));

        return  new PedidoItemResponse(
            item.getId(),
            item.getPedido().getId(),
            item.getProduto().getId(),
            item.getProduto().getNome(),
            item.getQuantidade(),
            item.getPrecoUnitario(),
            total,
            item.getObservacao(),
            item.getStatus()
        );
    }

}









