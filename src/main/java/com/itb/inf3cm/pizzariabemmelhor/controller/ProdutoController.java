package com.itb.inf3cm.pizzariabemmelhor.controller;

import org.springframework.web.bind.annotation.*;

import com.itb.inf3cm.pizzariabemmelhor.model.entity.Produto;
import com.itb.inf3cm.pizzariabemmelhor.model.services.CategoriaService;
import com.itb.inf3cm.pizzariabemmelhor.model.services.ProdutoService;
import com.itb.inf3cm.pizzariabemmelhor.dto.produto.*;;



@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {


    private final ProdutoService produtoService;
    private final CategoriaService categoriaService;

    public ProdutoController(ProdutoService produtoService, CategoriaService categoriaService) {
        this.produtoService = produtoService;
        this.categoriaService = categoriaService;
    }


    @PostMapping
    public ResponseEntity <Object> saveProduto(@RequestBody ProdutoRequest produtoRequest) {
        Produto produto = criarProduto(produtoRequest);

    return null;
   }


    private Produto criarProduto (ProdutoRequest produtoRequest) {

        Produto produto = new Produto();

        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setTipo(produtoRequest.getTipo());
        produto.setValorVenda(produtoRequest.getValorVenda());
        produto.setValorCompra(produtoRequest.getValorCompra());
        produto.setQuantidadeEstoque(produtoRequest.getQuantidadeEstoque());
        
        return produto;
    }


}
