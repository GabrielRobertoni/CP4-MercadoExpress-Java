package br.com.fiap.controller;

import br.com.fiap.model.ProdutoDTO;
import br.com.fiap.model.ProdutoModel;
import br.com.fiap.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para o Mercado Express.
 * 
 * Endpoints CRUD completos com HATEOAS (nível de maturidade 3).
 * 
 * Port: 8082 (configurado no application.properties)
 * Base URL: /api/mercado
 */
@RestController
@RequestMapping("/api/mercado")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    // ==================== CREATE ====================

    /**
     * Cria um novo produto.
     * 
     * Exemplo JSON:
     * {
     *   "nome": "Meia Infantil",
     *   "tipo": "Vestuário",
     *   "setor": "Roupas",
     *   "tamanho": "M",
     *   "preco": 12.90
     * }
     * 
     * @param dto Dados do produto
     * @return Produto criado com links HATEOAS
     */
    @PostMapping
    public ResponseEntity<ProdutoModel> criarProduto(@Valid @RequestBody ProdutoDTO dto) {
        ProdutoModel model = service.criarProduto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    // ==================== READ ====================

    /**
     * Lista todos os produtos do mercado.
     * 
     * @return Lista de produtos com links HATEOAS
     */
    @GetMapping
    public ResponseEntity<List<ProdutoModel>> listarTodos() {
        List<ProdutoModel> produtos = service.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    /**
     * Busca um produto pelo ID.
     * 
     * @param id ID do produto
     * @return Produto encontrado com links HATEOAS
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoModel> buscarPorId(@PathVariable Long id) {
        ProdutoModel model = service.buscarPorId(id);
        return ResponseEntity.ok(model);
    }

    /**
     * Busca produtos por nome.
     * 
     * @param nome Nome parcial do produto
     * @return Lista de produtos encontrados
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProdutoModel>> buscarPorNome(@RequestParam String nome) {
        List<ProdutoModel> produtos = service.buscarPorNome(nome);
        return ResponseEntity.ok(produtos);
    }

    /**
     * Busca produtos por setor.
     * 
     * @param setor Setor do produto
     * @return Lista de produtos do setor
     */
    @GetMapping("/setor/{setor}")
    public ResponseEntity<List<ProdutoModel>> buscarPorSetor(@PathVariable String setor) {
        List<ProdutoModel> produtos = service.buscarPorSetor(setor);
        return ResponseEntity.ok(produtos);
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza um produto existente (substituição completa - PUT).
     * 
     * @param id ID do produto
     * @param dto Novos dados do produto
     * @return Produto atualizado com links HATEOAS
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarProduto(@PathVariable Long id,
                                                          @Valid @RequestBody ProdutoDTO dto) {
        ProdutoModel model = service.atualizarProduto(id, dto);
        return ResponseEntity.ok(model);
    }

    /**
     * Atualiza parcialmente um produto (PATCH).
     * 
     * @param id ID do produto
     * @param dto Campos a serem atualizados
     * @return Produto atualizado com links HATEOAS
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ProdutoModel> atualizarParcialmente(@PathVariable Long id,
                                                               @RequestBody ProdutoDTO dto) {
        ProdutoModel model = service.atualizarParcialmente(id, dto);
        return ResponseEntity.ok(model);
    }

    // ==================== DELETE ====================

    /**
     * Remove um produto pelo ID.
     * 
     * @param id ID do produto a ser removido
     * @return Mensagem de confirmação
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarProduto(@PathVariable Long id) {
        service.deletarProduto(id);
        return ResponseEntity.ok("Produto com ID " + id + " removido com sucesso.");
    }
}
