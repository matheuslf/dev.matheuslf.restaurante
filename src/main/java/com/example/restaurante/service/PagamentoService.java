package com.example.restaurante.service;

import com.example.restaurante.client.PagamentoClient;
import com.example.restaurante.domain.FechamentoConta;
import com.example.restaurante.domain.Mesa;
import com.example.restaurante.domain.Pagamento;
import com.example.restaurante.domain.Pedido;
import com.example.restaurante.domain.enums.FormaPagamento;
import com.example.restaurante.domain.enums.StatusMesa;
import com.example.restaurante.domain.enums.StatusPagamento;
import com.example.restaurante.domain.enums.StatusPedido;
import com.example.restaurante.dto.PagamentoRequest;
import com.example.restaurante.dto.PagamentoResponse;
import com.example.restaurante.exception.RegraNegocioException;
import com.example.restaurante.repository.FechamentoContaRepository;
import com.example.restaurante.repository.MesaRepository;
import com.example.restaurante.repository.PagamentoRepository;
import com.example.restaurante.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private final PagamentoClient pagamentoClient;
    private final FechamentoContaRepository fechamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(
            PagamentoClient pagamentoClient,
            FechamentoContaRepository fechamentoRepository,
            PedidoRepository pedidoRepository,
            MesaRepository mesaRepository,
            PagamentoRepository pagamentoRepository
    ) {
        this.pagamentoClient = pagamentoClient;
        this.fechamentoRepository = fechamentoRepository;
        this.pedidoRepository = pedidoRepository;
        this.mesaRepository = mesaRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public void pagar(Long pedidoId, String formaPagamento) {

        FechamentoConta fechamento = fechamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RegraNegocioException("Conta não encontrada."));

        PagamentoResponse response = pagamentoClient.processar(
                new PagamentoRequest(
                        fechamento.getTotal().doubleValue(),
                        formaPagamento
                )
        );

        if ("APROVADO".equals(response.status())) {
            Pedido pedido = fechamento.getPedido();
            pedido.setStatus(StatusPedido.FECHADO);

            Mesa mesa = pedido.getMesa();
            mesa.setStatus(StatusMesa.LIVRE);

            Pagamento pagamento = new Pagamento();
            pagamento.setPedido(pedido);
            pagamento.setFormaPagamento(FormaPagamento.getFormaPagamento(formaPagamento));
            pagamento.setStatus(StatusPagamento.APROVADO);
            pagamento.setValor(fechamento.getTotal());
            pagamento.setDataPagamento(fechamento.getDataFechamento());

            pedidoRepository.save(pedido);
            mesaRepository.save(mesa);
            pagamentoRepository.save(pagamento);
        }
    }
}