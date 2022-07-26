package br.com.vr.avaliacaointermediario;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.vr.avaliacaointermediario.model.form.CartaoForm;
import br.com.vr.avaliacaointermediario.model.form.TransacaoForm;

@SpringBootTest
@AutoConfigureMockMvc
public class TransacaoControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void criaCartao() throws Exception {

        CartaoForm cartao = new CartaoForm("0123456789123456", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cartao);

        this.mockMvc
            .perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful());

	}

    @Test
    public void aoRealizarUmaTrancaoComUmCartaoInexistenteDeveriaDarErro() throws Exception {

        TransacaoForm transacao = new TransacaoForm(new BigDecimal("10.00"), "0123456789123457", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transacao);

        this.mockMvc
            .perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            // .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("CARTAO_INEXISTENTE")));

    }

    @Test
    public void aoRealizarUmaTrancaoComASenhaErradaDeveriaDarErro() throws Exception {

        TransacaoForm transacao = new TransacaoForm(new BigDecimal("10.00"), "0123456789123456", "1234");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transacao);

        this.mockMvc
            .perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("SENHA_INVALIDA")));

    }

    @Test
    public void aoRealizarUmaTrancaoComValorMaiorQueOSaldoDeveriaDarErro() throws Exception {

        TransacaoForm transacao = new TransacaoForm(new BigDecimal("1000.00"), "0123456789123456", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transacao);

        this.mockMvc
        .perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        //.andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(containsString("SALDO_INSUFICIENTE")));

    }

    @Test
    public void aoRealizarUmaTrancaoComTudoCertoDeveriaRetornarOKEOSaldoDoCartaoDeveriaDiminuir() throws Exception {

        TransacaoForm transacao = new TransacaoForm(new BigDecimal("100.00"), "0123456789123456", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(transacao);

        this.mockMvc
            .perform(post("/transacoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc
            .perform(get("/cartoes/0123456789123456"))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(containsString("400.00")));

    }
    
}
