package dev.matheuslf.restaurante.controller;

import dev.matheuslf.restaurante.dto.PedidoItemRequest;
import dev.matheuslf.restaurante.dto.PedidoItemResponse;
import dev.matheuslf.restaurante.dto.PedidoRequest;
import dev.matheuslf.restaurante.dto.PedidoResponse;
import dev.matheuslf.restaurante.service.PedidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse abrirPedido(@RequestBody PedidoRequest pedidoRequest) {
        return pedidoService.abrirPedido(pedidoRequest);
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
    public PedidoItemResponse adicionarItem(@PathVariable Long pedidoId, @RequestBody PedidoItemRequest request) {
        return pedidoService.adicionarItem(pedidoId, request);
    }

    @GetMapping("/{pedidoId}/itens")
    public List<PedidoItemResponse> listarItens(@PathVariable Long pedidoId) {
        return pedidoService.listarItens(pedidoId);
    }

}











