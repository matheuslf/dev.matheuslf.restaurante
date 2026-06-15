package com.example.restaurante.repository;

import com.example.restaurante.domain.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa, Long> {
}
