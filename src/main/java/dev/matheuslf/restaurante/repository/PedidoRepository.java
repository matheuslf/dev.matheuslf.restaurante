package dev.matheuslf.restaurante.repository;

import dev.matheuslf.restaurante.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
