package com.itb.inf3cm.pizzariabemmelhor.model.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itb.inf3cm.pizzariabemmelhor.model.entity.Categoria;
import com.itb.inf3cm.pizzariabemmelhor.model.entity.Produto;
import com.itb.inf3cm.pizzariabemmelhor.model.repository.ProdutoRepository;
import com.itb.inf3cm.pizzariabemmelhor.exceptions.*;



@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    
    public ProdutoService(ProdutoRepository produtoRepository, CategoriaService categoriaService) {
        this.produtoRepository = produtoRepository; 
        this.categoriaService = categoriaService;
    }


public Produto findById(Long id) {

    try {
         return produtoRepository.findById(id).get();
    } catch (Exception e) { 
        throw new NotFound("Produto não encontrado com o id " + id);
        
    }

}

@Transactional
public Produto save(Produto produto) {
   produto.setCodStatus(true);


   if(produto.getCategoria() != null) {

      Categoria categoria = categoriaService.findById(produto.getCategoria().getId());
      if(categoria == null) {
        throw new BadRequest("Não foi encontrado a categoria com o id " + produto.getCategoria().getId());
      }

   }
   return produtoRepository.save(produto);
}

public List<Produto> findAll() {
    return produtoRepository.findAll();
}



}
