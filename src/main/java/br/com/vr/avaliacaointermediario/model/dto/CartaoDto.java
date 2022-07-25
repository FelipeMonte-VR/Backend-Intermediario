package br.com.vr.avaliacaointermediario.model.dto;

import br.com.vr.avaliacaointermediario.model.Cartao;

public class CartaoDto {
    
    String numeroCartao;
    String senha;

    public CartaoDto(Cartao cartao) {
        this.numeroCartao = cartao.getNumeroCartao();
        this.senha = cartao.getSenha();
    }
    
    public String getNumeroCartao() {
        return numeroCartao;
    }
    
    public String getSenha() {
        return senha;
    }

    public static CartaoDto converte(Cartao cartao) {
        return new CartaoDto(cartao);
    }

}
