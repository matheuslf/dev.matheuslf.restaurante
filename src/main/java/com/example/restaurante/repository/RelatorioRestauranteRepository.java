package com.example.restaurante.repository;

import com.example.restaurante.domain.Pagamento;
import com.example.restaurante.projection.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface RelatorioRestauranteRepository extends Repository<Pagamento, Long> {

    @Query(value = """
        SELECT 
            TO_CHAR(p.data_pagamento, 'YYYY-MM-DD') AS data,
            SUM(p.valor) AS total
        FROM pagamentos p
        WHERE p.status = 'APROVADO'
        GROUP BY TO_CHAR(p.data_pagamento, 'YYYY-MM-DD')
        ORDER BY data
        """, nativeQuery = true)
    List<FaturamentoDiarioProjection> faturamentoDiario();

    @Query(value = """
        SELECT 
            pr.id AS produtoId,
            pr.nome AS produtoNome,
            SUM(pi.quantidade) AS quantidadeVendida,
            SUM(pi.quantidade * pi.preco_unitario) AS totalVendido
        FROM pedido_itens pi
        JOIN produtos pr ON pr.id = pi.produto_id
        JOIN pedidos pe ON pe.id = pi.pedido_id
        JOIN pagamentos pa ON pa.pedido_id = pe.id
        WHERE pa.status = 'APROVADO'
        GROUP BY pr.id, pr.nome
        ORDER BY quantidadeVendida DESC
        """, nativeQuery = true)
    List<ProdutoMaisVendidoProjection> produtosMaisVendidos();

    @Query(value = """
        SELECT 
            COALESCE(AVG(p.valor), 0) AS ticketMedio
        FROM pagamentos p
        WHERE p.status = 'APROVADO'
        """, nativeQuery = true)
    TicketMedioProjection ticketMedio();

    @Query(value = """
        SELECT 
            status,
            COUNT(*) AS quantidade
        FROM pedidos
        GROUP BY status
        ORDER BY quantidade DESC
        """, nativeQuery = true)
    List<PedidosPorStatusProjection> pedidosPorStatus();
}
