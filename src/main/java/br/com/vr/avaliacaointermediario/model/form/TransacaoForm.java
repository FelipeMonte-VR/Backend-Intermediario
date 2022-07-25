package br.com.vr.avaliacaointermediario.model.form;

import java.math.BigDecimal;

import br.com.vr.avaliacaointermediario.model.Cartao;
import br.com.vr.avaliacaointermediario.model.Transacao;

public class TransacaoForm {

    private BigDecimal valor;
    private String numeroCartao;
    private String senhaCartao;

    public TransacaoForm() {}

    public TransacaoForm(BigDecimal valor, String numeroCartao, String senhaCartao) {
        this.valor = valor;
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public static Transacao converte(BigDecimal valor, Cartao cartao) {
        return new Transacao(valor, cartao);
    }

}
