package br.com.fiap;

import br.com.fiap.model.ProdutoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes automatizados dos endpoints da API Mercado Express.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long produtoId;

    @Test
    @Order(1)
    void testCriarProduto() throws Exception {
        ProdutoDTO dto = ProdutoDTO.builder()
                .nome("Teste Meia")
                .tipo("Vestuário")
                .setor("Roupas")
                .tamanho("M")
                .preco(new BigDecimal("19.90"))
                .build();

        mockMvc.perform(post("/api/mercado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Teste Meia"))
                .andExpect(jsonPath("$.tipo").value("Vestuário"))
                .andExpect(jsonPath("$.setor").value("Roupas"))
                .andExpect(jsonPath("$.tamanho").value("M"))
                .andExpect(jsonPath("$.preco").value(19.90))
                .andExpect(jsonPath("$._links.self").exists())
                .andExpect(jsonPath("$._links.todos").exists());
    }

    @Test
    @Order(2)
    void testListarTodos() throws Exception {
        mockMvc.perform(get("/api/mercado")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(3)
    void testBuscarPorId() throws Exception {
        // Primeiro cria um produto
        ProdutoDTO dto = ProdutoDTO.builder()
                .nome("Produto Teste ID")
                .tipo("Limpeza")
                .setor("Higiene")
                .tamanho("1L")
                .preco(new BigDecimal("8.90"))
                .build();

        String response = mockMvc.perform(post("/api/mercado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        produtoId = objectMapper.readTree(response).get("id").asLong();

        // Depois busca pelo ID
        mockMvc.perform(get("/api/mercado/" + produtoId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(produtoId))
                .andExpect(jsonPath("$.nome").value("Produto Teste ID"));
    }

    @Test
    @Order(4)
    void testAtualizarProduto() throws Exception {
        // Cria um produto
        ProdutoDTO dto = ProdutoDTO.builder()
                .nome("Produto Antigo")
                .tipo("Alimento")
                .setor("Frutas")
                .tamanho("1kg")
                .preco(new BigDecimal("10.00"))
                .build();

        String response = mockMvc.perform(post("/api/mercado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        // Atualiza
        ProdutoDTO dtoAtualizado = ProdutoDTO.builder()
                .nome("Produto Atualizado")
                .tipo("Alimento")
                .setor("Frutas")
                .tamanho("2kg")
                .preco(new BigDecimal("15.00"))
                .build();

        mockMvc.perform(put("/api/mercado/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Atualizado"))
                .andExpect(jsonPath("$.tamanho").value("2kg"))
                .andExpect(jsonPath("$.preco").value(15.00));
    }

    @Test
    @Order(5)
    void testAtualizarParcialmente() throws Exception {
        ProdutoDTO dto = ProdutoDTO.builder()
                .nome("Produto Patch")
                .tipo("Bebida")
                .setor("Bebidas")
                .tamanho("500ml")
                .preco(new BigDecimal("5.00"))
                .build();

        String response = mockMvc.perform(post("/api/mercado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        // Atualiza apenas o preço
        ProdutoDTO dtoParcial = new ProdutoDTO();
        dtoParcial.setPreco(new BigDecimal("7.50"));

        mockMvc.perform(patch("/api/mercado/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoParcial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Patch"))  // mantém
                .andExpect(jsonPath("$.preco").value(7.50));            // atualiza
    }

    @Test
    @Order(6)
    void testDeletarProduto() throws Exception {
        ProdutoDTO dto = ProdutoDTO.builder()
                .nome("Produto para Deletar")
                .tipo("Vestuário")
                .setor("Roupas")
                .tamanho("P")
                .preco(new BigDecimal("9.90"))
                .build();

        String response = mockMvc.perform(post("/api/mercado")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        // Deleta
        mockMvc.perform(delete("/api/mercado/" + id))
                .andExpect(status().isOk());

        // Verifica que não existe mais
        mockMvc.perform(get("/api/mercado/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void testBuscarPorSetor() throws Exception {
        mockMvc.perform(get("/api/mercado/setor/Frutas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(8)
    void testBuscarPorNome() throws Exception {
        mockMvc.perform(get("/api/mercado/buscar?nome=Meia")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
