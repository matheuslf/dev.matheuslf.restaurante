package com.example.restaurante.controller;

import com.example.restaurante.dto.PedidoItemRequest;
import com.example.restaurante.dto.PedidoItemResponse;
import com.example.restaurante.dto.PedidoRequest;
import com.example.restaurante.dto.PedidoResponse;
import com.example.restaurante.service.PagamentoService;
import com.example.restaurante.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;
    private final PagamentoService pagamentoService;

    public PedidoController(PedidoService pedidoService, PagamentoService pagamentoService) {
        this.pedidoService = pedidoService;
        this.pagamentoService = pagamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse abrirPedido(@RequestBody PedidoRequest request) {
        return pedidoService.abrirPedido(request);
    }

    @GetMapping
    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public PedidoResponse buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id);
    }

    @PostMapping("/{pedidoId}/itens")
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoItemResponse adicionarItem(
            @PathVariable Long pedidoId,
            @RequestBody PedidoItemRequest request
    ) {
        return pedidoService.adicionarItem(pedidoId, request);
    }

    @GetMapping("/{pedidoId}/itens")
    public List<PedidoItemResponse> listarItens(@PathVariable Long pedidoId) {
        return pedidoService.listarItens(pedidoId);
    }

    @PostMapping("/{pedidoId}/pagar")
    public void pagar(
            @PathVariable Long pedidoId,
            @RequestParam String formaPagamento
    ) {
        pagamentoService.pagar(pedidoId, formaPagamento);
    }
}
