package br.com.fiap.model;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;
import java.math.BigDecimal;

/**
 * Modelo HATEOAS para Produto - nível de maturidade 3 (HATEOAS).
 * Estende RepresentationModel para incluir hiperlinks automáticos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    private Long id;
    private String nome;
    private String tipo;
    private String setor;
    private String tamanho;
    private BigDecimal preco;
}
