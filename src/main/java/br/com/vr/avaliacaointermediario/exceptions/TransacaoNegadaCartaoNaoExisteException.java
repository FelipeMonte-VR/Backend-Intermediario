package br.com.vr.avaliacaointermediario.exceptions;

public class TransacaoNegadaCartaoNaoExisteException extends Exception {

    public TransacaoNegadaCartaoNaoExisteException() {}

    public TransacaoNegadaCartaoNaoExisteException(Exception e) {
        super(e);
    }
    
}
