package br.com.vr.avaliacaointermediario.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.avaliacaointermediario.model.dto.CartaoCompletoDto;
import br.com.vr.avaliacaointermediario.exceptions.CartaoJaExisteException;
import br.com.vr.avaliacaointermediario.exceptions.CartaoNaoExisteException;
import br.com.vr.avaliacaointermediario.model.dto.CartaoDto;
import br.com.vr.avaliacaointermediario.model.form.CartaoForm;
import br.com.vr.avaliacaointermediario.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {
    
    @Autowired
    CartaoService cartaoService;
    
    @GetMapping
    public ResponseEntity<List<CartaoCompletoDto>> pegaCartoes() {

        List<CartaoCompletoDto> cartoes = cartaoService.pegaCartoes();

        return ResponseEntity.ok().body(cartoes);
        
    }
    
    @PostMapping
    public ResponseEntity<CartaoDto> criaCartao(@RequestBody @Valid CartaoForm cartaoForm) {

        CartaoDto cartao;
        try {
            cartao = cartaoService.criaCartao(cartaoForm);
        } catch (CartaoJaExisteException e) {
            return ResponseEntity.status(422).body(e.getCartao());
        }

        return ResponseEntity.status(201).body(cartao);

    }
    
    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> pegaSaldoCartao(@PathVariable("numeroCartao")  String numeroCartao) throws CartaoNaoExisteException {

        BigDecimal saldo = cartaoService.pegaSaldoCartao(numeroCartao);

        return ResponseEntity.ok().body(saldo);

    }

}
