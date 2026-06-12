package dev.matheuslf.restaurante.service;

import dev.matheuslf.restaurante.domain.entity.Mesa;
import dev.matheuslf.restaurante.domain.entity.Pedido;
import dev.matheuslf.restaurante.domain.entity.PedidoItem;
import dev.matheuslf.restaurante.domain.entity.Produto;
import dev.matheuslf.restaurante.domain.enums.StatusItemPedido;
import dev.matheuslf.restaurante.domain.enums.StatusMesa;
import dev.matheuslf.restaurante.domain.enums.StatusPedido;
import dev.matheuslf.restaurante.dto.PedidoItemRequest;
import dev.matheuslf.restaurante.dto.PedidoItemResponse;
import dev.matheuslf.restaurante.dto.PedidoRequest;
import dev.matheuslf.restaurante.dto.PedidoResponse;
import dev.matheuslf.restaurante.exception.RegraNegocioException;
import dev.matheuslf.restaurante.repository.MesaRepository;
import dev.matheuslf.restaurante.repository.PedidoItemRepository;
import dev.matheuslf.restaurante.repository.PedidoRepository;
import dev.matheuslf.restaurante.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;

    public PedidoService(PedidoRepository pedidoRepository, MesaRepository mesaRepository,  ProdutoRepository produtoRepository, PedidoItemRepository pedidoItemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.produtoRepository = produtoRepository;
        this.pedidoItemRepository = pedidoItemRepository;
    }

    public PedidoResponse abrirPedido(PedidoRequest pedidoRequest) {
        Mesa mesa = mesaRepository.findById(pedidoRequest.mesaId())
                .orElseThrow(() -> new RegraNegocioException("Mesa inexistente"));

        if (mesa.getStatus() != StatusMesa.LIVRE) {
            throw new RegraNegocioException("Mesa não está livre para abertura de pedido.");
        }

        Pedido pedido = new Pedido();
        pedido.setMesa(mesa);
        pedido.setStatus(StatusPedido.ABERTO);
        pedido.setObservacao(pedidoRequest.observacao());

        mesa.setStatus(StatusMesa.OCUPADA);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        mesaRepository.save(mesa);

        return PedidoResponse.fromEntity(pedidoSalvo);
    }

    public Page<PedidoResponse> listar(Pageable pageable) {
        return pedidoRepository.findAll(pageable).map(PedidoResponse::fromEntity);
    }

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = buscarPedidoPorId(id);
        return PedidoResponse.fromEntity(pedido);
    }

    public PedidoItemResponse adicionarItem(Long pedidoId, PedidoItemRequest request) {
        Pedido pedido = buscarPedidoPorId(pedidoId);
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException("Só é possível adicionar item em pedidos abertos.");
        }

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado."));

        if (!produto.getDisponivel()) {
            throw new RegraNegocioException("Produto indisponível no cardápior");
        }

        if (request.quantidade() == null || request.quantidade() <= 0) {
            throw new RegraNegocioException("A quantidade deve ser maior que zero.");
        }

        PedidoItem pedidoItem = new PedidoItem();
        pedidoItem.setPedido(pedido);
        pedidoItem.setProduto(produto);
        pedidoItem.setQuantidade(request.quantidade());
        pedidoItem.setPrecoUnitario(produto.getPreco());
        pedidoItem.setObservacao(request.observacao());
        pedidoItem.setStatus(StatusItemPedido.PENDENTE);
        PedidoItem itemSalvo = pedidoItemRepository.save(pedidoItem);
        return  PedidoItemResponse.fromEntity(itemSalvo);
    }

    public List<PedidoItemResponse> listarItens(Long pedidoId) {
        buscarPedidoPorId(pedidoId);

        return pedidoItemRepository.findByPedidoId(pedidoId).stream().map(PedidoItemResponse::fromEntity).collect(Collectors.toList());
    }

    private Pedido buscarPedidoPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("Pedido não encontrado."));
    }

}












