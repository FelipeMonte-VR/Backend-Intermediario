package br.com.vr.avaliacaointermediario.model.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.vr.avaliacaointermediario.model.Cartao;

public class CartaoForm {
    
    @NotNull @NotEmpty @Size(min=16, max=16) @Pattern(regexp="^(0|[1-9][0-9]*)$", message = "Only numbers are accepted and can not start with 0.") //(?=.{16}$)
    String numeroCartao;
    @NotNull @NotEmpty @Size(min=1, max=32)
    String senha;

    public CartaoForm(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getSenha() {
        return senha;
    }

    public static Cartao converte(CartaoForm cartaoForm) {
        return new Cartao(cartaoForm.numeroCartao, cartaoForm.senha);
    }
    
}
