package br.com.vr.avaliacaointermediario.exceptions;

public class CartaoNaoExisteException extends Exception {

    public CartaoNaoExisteException() {}

    public CartaoNaoExisteException(Exception e) {
        super(e);
    }

}
