package br.com.vr.avaliacaointermediario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.vr.avaliacaointermediario.controller.CartaoController;
import br.com.vr.avaliacaointermediario.model.form.CartaoForm;
import br.com.vr.avaliacaointermediario.model.form.TransacaoForm;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class TransacaoControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    CartaoController cartaoController;
    
    @BeforeEach
    public void criaCartao() throws Exception {
        cartaoController.criaCartao(new CartaoForm("1234567890123456", "123"));
	}
    
    @Test
    public void aoRealizarUmaTrancaoComValorNegativoOuNuloDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new TransacaoForm(new BigDecimal("-100.00"), "1234567890123456", "123"));

        this.mockMvc
            .perform(post("/transacoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            // .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("{\"field\":\"valor\",\"error\":\"must be greater than 0\"}")));

    }

    @Test
    public void aoRealizarUmaTrancaoComUmCartaoInexistenteDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new TransacaoForm(new BigDecimal("100.00"), "9876543210123456", "123"));

        this.mockMvc
            .perform(post("/transacoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            // .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("CARTAO_INEXISTENTE")));

    }

    @Test
    public void aoRealizarUmaTrancaoComASenhaErradaDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new TransacaoForm(new BigDecimal("100.00"), "1234567890123456", "321"));

        this.mockMvc
            .perform(post("/transacoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("SENHA_INVALIDA")));

    }

    @Test
    public void aoRealizarUmaTrancaoComValorMaiorQueOSaldoDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new TransacaoForm(new BigDecimal("1000.00"), "1234567890123456", "123"));

        this.mockMvc
            .perform(post("/transacoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("SALDO_INSUFICIENTE")));

    }


    @Test
    public void aoRealizarUmaTrancaoComTudoCertoDeveriaRetornarOKEOSaldoDoCartaoDeveriaDiminuir() throws Exception {

        String requestJson = criaRequestJson(new TransacaoForm(new BigDecimal("100.00"), "1234567890123456", "123"));

        this.mockMvc
            .perform(post("/transacoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful());
        
        this.mockMvc
            .perform(get("/cartoes/1234567890123456"))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(containsString("400.00")));

    }
    
    
    private String criaRequestJson(TransacaoForm transacaoForm) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(transacaoForm);

        return requestJson;

    }
    
}
