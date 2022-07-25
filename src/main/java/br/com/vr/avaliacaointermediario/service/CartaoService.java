package br.com.vr.avaliacaointermediario.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.avaliacaointermediario.exceptions.CartaoJaExisteException;
import br.com.vr.avaliacaointermediario.exceptions.CartaoNaoExisteException;
import br.com.vr.avaliacaointermediario.model.Cartao;
import br.com.vr.avaliacaointermediario.model.dto.CartaoCompletoDto;
import br.com.vr.avaliacaointermediario.model.dto.CartaoDto;
import br.com.vr.avaliacaointermediario.model.form.CartaoForm;
import br.com.vr.avaliacaointermediario.repository.CartaoRepository;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepo;

    public List<CartaoCompletoDto> pegaCartoes() {

        List<Cartao> cartoes = cartaoRepo.findAll();

        return CartaoCompletoDto.converte(cartoes);

    }

    public CartaoDto criaCartao(CartaoForm cartaoForm) throws CartaoJaExisteException {

        try {

            Cartao cartao = cartaoRepo.findById(cartaoForm.getNumeroCartao()).get();

            CartaoDto cartaoDto = CartaoDto.converte(cartao);

            throw new CartaoJaExisteException(cartaoDto);

        } catch (NoSuchElementException e) {

            //cenário ideal. Cartão novo, então cadastra.
            Cartao cartao = cartaoRepo.save(CartaoForm.converte(cartaoForm));
    
            return CartaoDto.converte(cartao);

        }

    }

    public BigDecimal pegaSaldoCartao(String numeroCartao) throws CartaoNaoExisteException {
        
        Cartao cartao = null;

        try {
            cartao = cartaoRepo.findById(numeroCartao).get();
        } catch (NoSuchElementException e) {
            throw new CartaoNaoExisteException(e);
        }

        return cartao.getSaldo();

    }

}
