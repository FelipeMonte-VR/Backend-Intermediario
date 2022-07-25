package br.com.vr.avaliacaointermediario.exceptions;

import br.com.vr.avaliacaointermediario.model.dto.CartaoDto;

public class CartaoJaExisteException extends Exception{
    
    CartaoDto cartao;

    public CartaoJaExisteException(CartaoDto cartao) {
        this.cartao = cartao;
    }
    
    public CartaoJaExisteException(Exception e, CartaoDto cartao) {
        super(e);
        this.cartao = cartao;
    }

    public CartaoDto getCartao() {
        return cartao;
    }

}
