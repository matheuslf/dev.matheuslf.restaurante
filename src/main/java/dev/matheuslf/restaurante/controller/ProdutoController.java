package dev.matheuslf.restaurante.controller;

import dev.matheuslf.restaurante.dto.ProdutoRequest;
import dev.matheuslf.restaurante.dto.ProdutoResponse;
import dev.matheuslf.restaurante.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse cadastrar(@RequestBody ProdutoRequest produtoRequest) {
        return produtoService.cadastrar(produtoRequest);
    }

    @GetMapping
    public Page<ProdutoResponse> listar(Pageable pageable) {
        return produtoService.listar(pageable);
    }

    @GetMapping("/{id}")
    public ProdutoResponse buscarPorId(@PathVariable Long id){
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoResponse atualizar(@PathVariable Long id, ProdutoRequest produtoRequest){
        return produtoService.atualizar(id, produtoRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){
        produtoService.excluir(id);
    }

}









