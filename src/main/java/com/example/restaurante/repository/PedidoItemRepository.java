package com.example.restaurante.repository;

import com.example.restaurante.domain.PedidoItem;
import com.example.restaurante.domain.enums.StatusItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
    List<PedidoItem> findByPedidoId(Long pedidoId);

    List<PedidoItem> findByStatusOrderByIdAsc(StatusItemPedido status);

    List<PedidoItem> findByPedidoIdAndStatusNot(Long pedidoId, StatusItemPedido status);

    @Query("""
        SELECT i
        FROM PedidoItem i
        JOIN FETCH i.produto
        JOIN FETCH i.pedido p
        JOIN FETCH p.mesa
        WHERE i.status = :status
        ORDER BY i.id
    """)
    List<PedidoItem> buscarItensComProdutoEpedido(StatusItemPedido status);
}
