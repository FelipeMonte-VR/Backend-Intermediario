package br.com.vr.avaliacaointermediario;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import br.com.vr.avaliacaointermediario.model.form.CartaoForm;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class CartaoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
	public void aoCriarUmCartaoComMaisOuMenosDigitosQue16DeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("123456789012345", "123"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            // //comparação usando string
            // .andExpect(content().string(containsString("\"field\":\"numeroCartao\",\"error\":\"size must be between 16 and 16\"")))
            //comparação com o array de json usando posição determinada (OBS.: a resposta pode trocar a ordem dos itens dependendo do critério de ordenação)
            .andExpect(jsonPath("$[0].field").value("numeroCartao"))
            .andExpect(jsonPath("$[0].error").value("size must be between 16 and 16")); //se o local não for US vai falhar o teste
            // //comparação com o array de json ignorando a posição
            // .andExpect(jsonPath("$[*].field", containsInAnyOrder("numeroCartao")))
            // .andExpect(jsonPath("$[*].error", containsInAnyOrder("size must be between 16 and 16")));

    }

    @Test
	public void aoCriarUmCartaoIniciandoCom0DeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("0123456789123456", "123"));

        this.mockMvc
        .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("{\"field\":\"numeroCartao\",\"error\":\"must match \\\"^(0|[1-9][0-9]*)$\\\"\"}")));

    }

    @Test
	public void aoCriarUmCartaoComAlgarismosExcetoNumerosDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("123456789012345a", "123"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("{\"field\":\"numeroCartao\",\"error\":\"must match \\\"^(0|[1-9][0-9]*)$\\\"\"}")));

    }

    @Test
	public void aoCriarUmCartaoSemNumeroDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("", "123"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("{\"field\":\"numeroCartao\",\"error\":\"must not be empty\"}")));

	}

    @Test
	public void aoCriarUmCartaoSemSenhaDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("1234567890123456", ""));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("{\"field\":\"senha\",\"error\":\"must not be empty\"}")));

	}

    @Test
	public void aoCriarUmCartaoComSenhaMaiorQue32CaracteresDeveriaDarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("1234567890123456", "012345678901234567890123456789012"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(content().string(containsString("{\"field\":\"senha\",\"error\":\"size must be between 1 and 32\"}")));

	}

    @Test
	public void aoCriarUmCartaoOSaldoDeveriaSer500() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("1234567890123456", "123"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
            .andExpect(jsonPath("$.senha").value("123"));

        this.mockMvc
            .perform(get("/cartoes/1234567890123456"))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().string(containsString("500.00")));

	}

    @Test
    public void aoTerntarCriarUmCartaoComOMesmoNumeroDeOutroQueJaExisteDeveriaRetornarErro() throws Exception {

        String requestJson = criaRequestJson(new CartaoForm("1234567890123456", "123"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
            .andExpect(jsonPath("$.senha").value("123"));

        this.mockMvc
            .perform(post("/cartoes").header("Accept-Language", "en-US").contentType(MediaType.APPLICATION_JSON).content(requestJson))
            //.andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.numeroCartao").value("1234567890123456"))
            .andExpect(jsonPath("$.senha").value("123"));

	}

    @Test
	public void aoConsultarSaldoDeUmCartaoNaoCadastradoDeveriaReceberErro404() throws Exception {

        this.mockMvc
            .perform(get("/cartoes/1234567890123456"))
            //.andDo(print())
            .andExpect(status().is4xxClientError());

	}

    private String criaRequestJson(CartaoForm cartaoForm) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(cartaoForm);

        return requestJson;

    }

}
