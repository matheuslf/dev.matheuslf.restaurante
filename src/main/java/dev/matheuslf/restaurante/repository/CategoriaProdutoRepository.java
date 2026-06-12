package dev.matheuslf.restaurante.repository;

import dev.matheuslf.restaurante.domain.entity.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
}
