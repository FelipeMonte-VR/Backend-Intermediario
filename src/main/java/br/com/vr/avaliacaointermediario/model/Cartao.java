package br.com.vr.avaliacaointermediario.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSaldoInsuficiente;

@Entity
@Table(name = "cartoes")
public class Cartao {
    
    @Id
    @Column(name = "numero", nullable = false, unique = true, columnDefinition = "VARCHAR(16)")
    String numeroCartao;
    String senha;

    BigDecimal saldo = new BigDecimal("500.00");

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Transacao> transacoes;

    public Cartao() {}

    public Cartao(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        saldo = new BigDecimal("500.00");
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

    public void fazDebito(BigDecimal valor) throws TransacaoNegadaSaldoInsuficiente {
        
        if (saldo.compareTo(valor) == -1) {
            throw new TransacaoNegadaSaldoInsuficiente();
        }

        MathContext precisao = new MathContext(2); // precisão de duas casas

        saldo = saldo.subtract(valor, precisao);
        
    }
    
}