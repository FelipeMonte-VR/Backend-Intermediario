package br.com.vr.avaliacaointermediario.exceptions;

public class TransacaoNegadaSaldoInsuficiente extends Exception {
    
    public TransacaoNegadaSaldoInsuficiente() {}

    public TransacaoNegadaSaldoInsuficiente(Exception e) {
        super(e);
    }

}
