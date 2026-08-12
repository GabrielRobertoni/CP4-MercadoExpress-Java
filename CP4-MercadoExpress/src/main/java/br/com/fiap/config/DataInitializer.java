package br.com.fiap.config;

import br.com.fiap.model.Produto;
import br.com.fiap.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Inicializador de dados - popula o banco com produtos de exemplo.
 * Simula um mercado express com diversos tipos de produtos.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProdutoRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // Produtos de Vestuário
        repository.save(Produto.builder()
                .nome("Meia Infantil Estampada")
                .tipo("Vestuário")
                .setor("Roupas")
                .tamanho("P")
                .preco(new BigDecimal("12.90"))
                .build());

        repository.save(Produto.builder()
                .nome("Meia Adulto Algodão")
                .tipo("Vestuário")
                .setor("Roupas")
                .tamanho("G")
                .preco(new BigDecimal("15.90"))
                .build());

        // Produtos de Limpeza
        repository.save(Produto.builder()
                .nome("Detergente Líquido 500ml")
                .tipo("Limpeza")
                .setor("Higiene")
                .tamanho("500ml")
                .preco(new BigDecimal("4.99"))
                .build());

        repository.save(Produto.builder()
                .nome("Desinfetante Pinho")
                .tipo("Limpeza")
                .setor("Higiene")
                .tamanho("1L")
                .preco(new BigDecimal("7.50"))
                .build());

        repository.save(Produto.builder()
                .nome("Sabão em Pó")
                .tipo("Limpeza")
                .setor("Higiene")
                .tamanho("1kg")
                .preco(new BigDecimal("18.90"))
                .build());

        // Frutas
        repository.save(Produto.builder()
                .nome("Banana Prata")
                .tipo("Alimento")
                .setor("Frutas")
                .tamanho("1kg")
                .preco(new BigDecimal("5.99"))
                .build());

        repository.save(Produto.builder()
                .nome("Maçã Fuji")
                .tipo("Alimento")
                .setor("Frutas")
                .tamanho("1kg")
                .preco(new BigDecimal("8.99"))
                .build());

        repository.save(Produto.builder()
                .nome("Laranja Pera")
                .tipo("Alimento")
                .setor("Frutas")
                .tamanho("1kg")
                .preco(new BigDecimal("4.50"))
                .build());

        // Bebidas
        repository.save(Produto.builder()
                .nome("Água Mineral 1.5L")
                .tipo("Bebida")
                .setor("Bebidas")
                .tamanho("1.5L")
                .preco(new BigDecimal("2.99"))
                .build());

        repository.save(Produto.builder()
                .nome("Suco de Laranja Integral")
                .tipo("Bebida")
                .setor("Bebidas")
                .tamanho("1L")
                .preco(new BigDecimal("9.90"))
                .build());

        // Snacks
        repository.save(Produto.builder()
                .nome("Biscoito Cream Cracker")
                .tipo("Alimento")
                .setor("Mercearia")
                .tamanho("400g")
                .preco(new BigDecimal("6.49"))
                .build());

        repository.save(Produto.builder()
                .nome("Chocolate ao Leite")
                .tipo("Alimento")
                .setor("Doces")
                .tamanho("90g")
                .preco(new BigDecimal("5.90"))
                .build());

        System.out.println("✅ Dados de exemplo inseridos com sucesso no Mercado Express!");
    }
}
