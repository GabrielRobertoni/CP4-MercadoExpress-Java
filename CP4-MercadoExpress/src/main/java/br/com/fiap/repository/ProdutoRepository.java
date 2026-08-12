package br.com.fiap.repository;

import br.com.fiap.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA para a tabela TDS_TB_mercado.
 * Herda de JpaRepository para operações CRUD automáticas.
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Busca produtos por nome (contém, case-insensitive).
     */
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    /**
     * Busca produtos por setor.
     */
    List<Produto> findBySetor(String setor);

    /**
     * Busca produtos por tipo.
     */
    List<Produto> findByTipo(String tipo);
}
