package com.example.restaurante.dto;

import com.example.restaurante.domain.CategoriaProduto;
import com.example.restaurante.domain.Produto;

import java.math.BigDecimal;

public record ProdutoRequest(
        Long categoriaId,
        String nome,
        String descricao,
        BigDecimal preco,
        Boolean disponivel,
        Integer tempoPreparoMinutos
) {

    public Produto toEntity(CategoriaProduto categoria) {
        Produto produto = new Produto();
        preencher(produto, categoria);
        return produto;
    }

    public void preencher(Produto produto, CategoriaProduto categoria) {
        produto.setCategoria(categoria);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setDisponivel(disponivel != null ? disponivel : true);
        produto.setTempoPreparoMinutos(tempoPreparoMinutos);
    }
}
