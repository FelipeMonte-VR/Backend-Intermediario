package br.com.vr.avaliacaointermediario.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaCartaoNaoExisteException;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSaldoInsuficiente;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSenhaInvalidaException;
import br.com.vr.avaliacaointermediario.model.Cartao;
import br.com.vr.avaliacaointermediario.model.Transacao;
import br.com.vr.avaliacaointermediario.model.dto.TransacaoDto;
import br.com.vr.avaliacaointermediario.model.form.TransacaoForm;
import br.com.vr.avaliacaointermediario.repository.CartaoRepository;
import br.com.vr.avaliacaointermediario.repository.TransacaoRepository;

@Service
public class TransacaoService {

    @Autowired
    CartaoRepository cartaoRepository;

    @Autowired
    TransacaoRepository transacaoRepository;
    
    public List<TransacaoDto> pegaTransacoes() {

        List<Transacao> transacoes = transacaoRepository.findAll();

        return TransacaoDto.converte(transacoes);

    }

    public void fazTransacao(TransacaoForm transacaoForm) throws
    TransacaoNegadaCartaoNaoExisteException,
    TransacaoNegadaSenhaInvalidaException,
    TransacaoNegadaSaldoInsuficiente {

        Cartao cartao = null;
        
        try {
            cartao = cartaoRepository.findById(transacaoForm.getNumeroCartao()).get();
        } catch (NoSuchElementException e) {
            throw new TransacaoNegadaCartaoNaoExisteException();
        }

        if (!cartao.getSenha().equals(transacaoForm.getSenhaCartao())) {
            throw new TransacaoNegadaSenhaInvalidaException();
        }

        cartao.fazDebito(transacaoForm.getValor());

        transacaoRepository.save(TransacaoForm.converte(transacaoForm.getValor(), cartao));

    }
    
}
