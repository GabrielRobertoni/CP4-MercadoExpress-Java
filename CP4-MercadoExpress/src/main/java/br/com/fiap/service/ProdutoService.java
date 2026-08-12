package br.com.fiap.service;

import br.com.fiap.assembler.ProdutoModelAssembler;
import br.com.fiap.model.Produto;
import br.com.fiap.model.ProdutoDTO;
import br.com.fiap.model.ProdutoModel;
import br.com.fiap.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer para operações de negócio com Produto.
 * Utiliza HATEOAS para retorno com hiperlinks.
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoModelAssembler assembler;

    // ==================== CREATE ====================

    /**
     * Cria um novo produto no banco de dados.
     * Endpoint: POST /api/mercado
     */
    @Transactional
    public ProdutoModel criarProduto(ProdutoDTO dto) {
        Produto produto = converterDtoParaProduto(dto);
        Produto salvo = repository.save(produto);
        return assembler.toModel(salvo);
    }

    // ==================== READ ====================

    /**
     * Lista todos os produtos com links HATEOAS.
     * Endpoint: GET /api/mercado
     */
    public List<ProdutoModel> listarTodos() {
        return repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Busca um produto pelo ID com links HATEOAS.
     * Endpoint: GET /api/mercado/{id}
     */
    public ProdutoModel buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        return assembler.toModel(produto);
    }

    /**
     * Busca produtos por nome.
     * Endpoint: GET /api/mercado/buscar?nome=xxx
     */
    public List<ProdutoModel> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    /**
     * Busca produtos por setor.
     * Endpoint: GET /api/mercado/setor/{setor}
     */
    public List<ProdutoModel> buscarPorSetor(String setor) {
        return repository.findBySetor(setor).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza um produto existente (PUT - substituição completa).
     * Endpoint: PUT /api/mercado/{id}
     */
    @Transactional
    public ProdutoModel atualizarProduto(Long id, ProdutoDTO dto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        existente.setNome(dto.getNome());
        existente.setTipo(dto.getTipo());
        existente.setSetor(dto.getSetor());
        existente.setTamanho(dto.getTamanho());
        existente.setPreco(dto.getPreco());

        Produto atualizado = repository.save(existente);
        return assembler.toModel(atualizado);
    }

    /**
     * Atualiza parcialmente um produto (PATCH - atualização parcial).
     * Endpoint: PATCH /api/mercado/{id}
     */
    @Transactional
    public ProdutoModel atualizarParcialmente(Long id, ProdutoDTO dto) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));

        if (dto.getNome() != null) existente.setNome(dto.getNome());
        if (dto.getTipo() != null) existente.setTipo(dto.getTipo());
        if (dto.getSetor() != null) existente.setSetor(dto.getSetor());
        if (dto.getTamanho() != null) existente.setTamanho(dto.getTamanho());
        if (dto.getPreco() != null) existente.setPreco(dto.getPreco());

        Produto atualizado = repository.save(existente);
        return assembler.toModel(atualizado);
    }

    // ==================== DELETE ====================

    /**
     * Remove um produto pelo ID.
     * Endpoint: DELETE /api/mercado/{id}
     */
    @Transactional
    public void deletarProduto(Long id) {
        Produto existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        repository.delete(existente);
    }

    // ==================== MÉTODOS AUXILIARES ====================

    private Produto converterDtoParaProduto(ProdutoDTO dto) {
        return Produto.builder()
                .nome(dto.getNome())
                .tipo(dto.getTipo())
                .setor(dto.getSetor())
                .tamanho(dto.getTamanho())
                .preco(dto.getPreco())
                .build();
    }
}
