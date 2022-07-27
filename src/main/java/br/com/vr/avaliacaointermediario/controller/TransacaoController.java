package br.com.vr.avaliacaointermediario.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaCartaoNaoExisteException;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSaldoInsuficiente;
import br.com.vr.avaliacaointermediario.exceptions.TransacaoNegadaSenhaInvalidaException;
import br.com.vr.avaliacaointermediario.model.dto.TransacaoDto;
import br.com.vr.avaliacaointermediario.model.form.TransacaoForm;
import br.com.vr.avaliacaointermediario.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    
    @Autowired
    TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<List<TransacaoDto>> pegaTransacaoes() {

        List<TransacaoDto> transacoes = transacaoService.pegaTransacoes();

        return ResponseEntity.ok().body(transacoes);
        
    }
    
    @PostMapping
    public ResponseEntity<String> fazTransacao(@Valid @RequestBody TransacaoForm transacaoForm) throws
    TransacaoNegadaCartaoNaoExisteException,
    TransacaoNegadaSenhaInvalidaException,
    TransacaoNegadaSaldoInsuficiente {

        transacaoService.fazTransacao(transacaoForm);
        
        return ResponseEntity.status(201).body("OK");
        
    }

}
