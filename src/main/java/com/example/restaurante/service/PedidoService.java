package com.example.restaurante.service;

import com.example.restaurante.domain.Mesa;
import com.example.restaurante.domain.Pedido;
import com.example.restaurante.domain.PedidoItem;
import com.example.restaurante.domain.Produto;
import com.example.restaurante.domain.enums.StatusItemPedido;
import com.example.restaurante.domain.enums.StatusMesa;
import com.example.restaurante.domain.enums.StatusPedido;
import com.example.restaurante.dto.PedidoItemRequest;
import com.example.restaurante.dto.PedidoItemResponse;
import com.example.restaurante.dto.PedidoRequest;
import com.example.restaurante.dto.PedidoResponse;
import com.example.restaurante.exception.RegraNegocioException;
import com.example.restaurante.repository.MesaRepository;
import com.example.restaurante.repository.PedidoItemRepository;
import com.example.restaurante.repository.PedidoRepository;
import com.example.restaurante.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoService(
            PedidoRepository pedidoRepository,
            MesaRepository mesaRepository,
            ProdutoRepository produtoRepository,
            PedidoItemRepository pedidoItemRepository
    ) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public PedidoResponse abrirPedido(PedidoRequest request) {
        Mesa mesa = mesaRepository.findById(request.mesaId())
                .orElseThrow(() -> new RegraNegocioException("Mesa não encontrada."));

        if (mesa.getStatus() != StatusMesa.LIVRE) {
            throw new RegraNegocioException("Mesa não está livre para abertura de pedido.");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setObservacao(request.observacao());

        mesa.setStatus(StatusMesa.OCUPADA);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        mesaRepository.save(mesa);

        return PedidoResponse.fromEntity(pedidoSalvo);
    }

    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoRepository.findAll(pageable)
                .map(PedidoResponse::fromEntity);
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarPedidoPorId(id);

        return PedidoResponse.fromEntity(pedido);
    }

    public PedidoItemResponse adicionarItem(Long pedidoId, PedidoItemRequest request) {
        Pedido pedido = buscarPedidoPorId(pedidoId);

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException("Só é possível adicionar itens em pedidos abertos.");
        }

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado."));

        if (!produto.getDisponivel()) {
            throw new RegraNegocioException("Produto indisponível no cardápio.");
        }

        if (request.quantidade() == null || request.quantidade() <= 0) {
            throw new RegraNegocioException("A quantidade deve ser maior que zero.");
        }

        PedidoItem item = new PedidoItem();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(request.quantidade());
        item.setPrecoUnitario(produto.getPreco());
        item.setObservacao(request.observacao());
        item.setStatus(StatusItemPedido.PENDENTE);

        PedidoItem itemSalvo = pedidoItemRepository.save(item);

        return PedidoItemResponse.fromEntity(itemSalvo);
    }

    public List<PedidoItemResponse> listarItens(Long pedidoId) {
        buscarPedidoPorId(pedidoId);

        return pedidoItemRepository.findByPedidoId(pedidoId)
                .stream()
                .map(PedidoItemResponse::fromEntity)
                .toList();
    }

    private Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado."));
    }
}
