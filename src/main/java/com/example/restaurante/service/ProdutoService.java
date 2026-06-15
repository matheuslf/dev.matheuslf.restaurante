package com.example.restaurante.service;

import com.example.restaurante.domain.CategoriaProduto;
import com.example.restaurante.domain.Produto;
import com.example.restaurante.dto.ProdutoRequest;
import com.example.restaurante.dto.ProdutoResponse;
import com.example.restaurante.repository.CategoriaProdutoRepository;
import com.example.restaurante.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public ProdutoService(
            ProdutoRepository produtoRepository,
            CategoriaProdutoRepository categoriaProdutoRepository
    ) {
        this.produtoRepository = produtoRepository;
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    public ProdutoResponse cadastrar(ProdutoRequest request) {
        CategoriaProduto categoria = buscarCategoriaPorId(request.categoriaId());

        Produto produto = request.toEntity(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);

        return ProdutoResponse.fromEntity(produtoSalvo);
    }

    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(ProdutoResponse::fromEntity);
    }

    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = buscarProdutoPorId(id);
        return ProdutoResponse.fromEntity(produto);
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest request) {
        Produto produto = buscarProdutoPorId(id);
        CategoriaProduto categoria = buscarCategoriaPorId(request.categoriaId());

        request.preencher(produto, categoria);

        Produto produtoAtualizado = produtoRepository.save(produto);

        return ProdutoResponse.fromEntity(produtoAtualizado);
    }

    public void excluir(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }

    private Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }

    private CategoriaProduto buscarCategoriaPorId(Long id) {
        return categoriaProdutoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));
    }
}
