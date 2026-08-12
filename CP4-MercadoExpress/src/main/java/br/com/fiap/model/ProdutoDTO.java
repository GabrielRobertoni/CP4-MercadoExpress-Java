package br.com.fiap.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;

/**
 * DTO para receber dados de Produto via JSON (POST/PUT/PATCH).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String nome;

    private String tipo;
    private String setor;
    private String tamanho;

    @Positive(message = "O preço deve ser positivo")
    private BigDecimal preco;
}
