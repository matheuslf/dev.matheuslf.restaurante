package dev.matheuslf.restaurante.repository;

import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {

    List<PedidoItem> findByPedidoId(Long pedidoId);

    List<PedidoItem> findByStatusOrderByIdAsc(StatusItemPedido status);

    List<PedidoItem> findByPedidoIdAndStatusNot(Long pedidoId, StatusItemPedido status);

}
