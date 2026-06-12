package dev.matheuslf.restaurante.repository;

import dev.matheuslf.restaurante.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
