package br.com.vr.avaliacaointermediario.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.vr.avaliacaointermediario.model.Transacao;

public class TransacaoDto {

    private Long id;
    private BigDecimal valor;
    private LocalDateTime data_hora;
    private String numeroCartao;

    TransacaoDto() {}

    TransacaoDto(Transacao transacao) {
        this.id = transacao.getId();
        this.valor = transacao.getValor();
        this.data_hora = transacao.getData_hora();
        this.numeroCartao = transacao.getCartao().getNumeroCartao();
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
    public String getNumeroCartao() {
        return numeroCartao;
    }
    public static List<TransacaoDto> converte(List<Transacao> transacoes) {
        return transacoes.stream().map(TransacaoDto::new).collect(Collectors.toList());
    }

}
