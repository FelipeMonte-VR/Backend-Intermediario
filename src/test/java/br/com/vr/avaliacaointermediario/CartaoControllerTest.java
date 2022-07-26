package br.com.vr.avaliacaointermediario;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.vr.avaliacaointermediario.model.form.CartaoForm;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CartaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
	public void aoCriarUmCartaoComMaisOuMenosDigitosQue16DeveriaDarErro() throws Exception {

        CartaoForm cartao = new CartaoForm("0123456789123456789", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cartao);

        this.mockMvc
            .perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError());

	}

    @Test
	public void aoCriarUmCartaoOSaldoDeveriaSer500() throws Exception {

        CartaoForm cartao = new CartaoForm("0123456789123456", "123");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(cartao);

        this.mockMvc
            .perform(post("/cartoes").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful());

        this.mockMvc
            .perform(get("/cartoes/0123456789123456"))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(containsString("500.00")));

	}

    @Test
	public void aoAcessarUmCartaoNaoCadastradoDeveriaReceberErro404() throws Exception {

        this.mockMvc
            .perform(get("/cartoes/0123456789123457"))
            .andDo(print())
            .andExpect(status().is4xxClientError());

	}

}
