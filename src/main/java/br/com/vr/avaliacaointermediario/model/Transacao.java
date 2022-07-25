package br.com.vr.avaliacaointermediario.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transacoes")
public class Transacao {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal valor;
    LocalDateTime data_hora = LocalDateTime.now();

    @ManyToOne(optional = true)
    @JoinColumn(name = "numero_cartao")
    private Cartao cartao;

    public Transacao() {}
    
    public Transacao(BigDecimal valor, Cartao cartao) {
        this.valor = valor;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public Cartao getCartao() {
        return cartao;
    }

}
