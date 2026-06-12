package dev.matheuslf.restaurante.repository;

import dev.matheuslf.restaurante.domain.entity.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
}
