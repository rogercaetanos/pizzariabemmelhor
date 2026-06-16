package com.itb.inf3cm.pizzariabemmelhor.dto.produto;

import lombok.*;


@Setter
@Getter
public class ProdutoRequest {

    private String nome;
    private String descricao;
    private double valorVenda;
    private double valorCompra;
    private String tipo;
    private int quantidadeEstoque;
    private boolean codStatus;
    private Long categoriaId;

}
