package dev.matheuslf.restaurante.repository;

import dev.matheuslf.restaurante.domain.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
