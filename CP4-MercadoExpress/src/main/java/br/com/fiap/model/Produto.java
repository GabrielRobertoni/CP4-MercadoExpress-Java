package br.com.fiap.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Entity Produto - representa um produto do Mercado Express.
 * 
 * Colunas da tabela: Id, Nome, Tipo, Setor, Tamanho e Preço.
 */
@Entity
@Table(name = "TDS_TB_mercado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "setor", length = 100)
    private String setor;

    @Column(name = "tamanho", length = 50)
    private String tamanho;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;
}
