package com.example.restaurante.controller;

import com.example.restaurante.projection.*;
import com.example.restaurante.repository.RelatorioRestauranteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioRestauranteRepository repository;

    public RelatorioController(RelatorioRestauranteRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/faturamento-diario")
    public List<FaturamentoDiarioProjection> faturamentoDiario() {
        return repository.faturamentoDiario();
    }

    @GetMapping("/produtos-mais-vendidos")
    public List<ProdutoMaisVendidoProjection> produtosMaisVendidos() {
        return repository.produtosMaisVendidos();
    }

    @GetMapping("/ticket-medio")
    public TicketMedioProjection ticketMedio() {
        return repository.ticketMedio();
    }

    @GetMapping("/pedidos-por-status")
    public List<PedidosPorStatusProjection> pedidosPorStatus() {
        return repository.pedidosPorStatus();
    }
}