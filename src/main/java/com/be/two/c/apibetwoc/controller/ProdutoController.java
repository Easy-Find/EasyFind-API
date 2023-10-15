package com.be.two.c.apibetwoc.controller;

import com.be.two.c.apibetwoc.dto.CadastroProdutoDto;
import com.be.two.c.apibetwoc.model.Produto;
import com.be.two.c.apibetwoc.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(){
        List<Produto> produtos = produtoService.listarProdutos();

        if(produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> listarProdutoPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.listarProdutoPorId(id));
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody CadastroProdutoDto produto){
        return ResponseEntity.ok(produtoService.cadastrarProduto(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody CadastroProdutoDto produto){
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id){
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Void> statusProduto(@PathVariable Long id, @RequestParam boolean status){
        produtoService.statusProduto(status, id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/promocao/{id}")
    public ResponseEntity<Void> statusPromocaoProduto(@PathVariable Long id, @RequestParam boolean status){
        produtoService.statusPromocaoProduto(id, status);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/estabelecimento/{id}")
    public ResponseEntity<List<Produto>> produtoPorEstabelecimento(@PathVariable Long id){
        List<Produto> produtos = produtoService.produtoPorEstabelecimento(id);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/pesquisa")
    public ResponseEntity<List<Produto>> pesquisa(@RequestParam String pesquisa){
        List<Produto> produtos = produtoService.barraDePesquisa(pesquisa);
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/promocao")
    public ResponseEntity<List<Produto>> produtoEmPromocao(){
        List<Produto> produtos = produtoService.produtoEmPromocao();
        if (produtos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }
}