package br.com.vr.avaliacaointermediario.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import br.com.vr.avaliacaointermediario.model.Cartao;

public class CartaoCompletoDto {
    
    String numeroCartao;
    String senha;
    BigDecimal saldo;

    public CartaoCompletoDto(Cartao cartao) {
        this.numeroCartao = cartao.getNumeroCartao();
        this.senha = cartao.getSenha();
        this.saldo = cartao.getSaldo();
    }
    
    public static List<CartaoCompletoDto> converte(List<Cartao> cartoes) {
        return cartoes.stream().map(CartaoCompletoDto::new).collect(Collectors.toList());
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

}
