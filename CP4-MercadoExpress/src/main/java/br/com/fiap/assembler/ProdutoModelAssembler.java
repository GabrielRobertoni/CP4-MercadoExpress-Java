package br.com.fiap.assembler;

import br.com.fiap.controller.ProdutoController;
import br.com.fiap.model.Produto;
import br.com.fiap.model.ProdutoModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class ProdutoModelAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoModel> {

    public ProdutoModelAssembler() {
        super(ProdutoController.class, ProdutoModel.class);
    }

    @Override
    public ProdutoModel toModel(Produto produto) {
        ProdutoModel model = ProdutoModel.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .tipo(produto.getTipo())
                .setor(produto.getSetor())
                .tamanho(produto.getTamanho())
                .preco(produto.getPreco())
                .build();

        // Link para o próprio recurso (self)
        model.add(linkTo(methodOn(ProdutoController.class).buscarPorId(produto.getId())).withSelfRel());

        // Link para listar todos os produtos
        model.add(linkTo(methodOn(ProdutoController.class).listarTodos()).withRel("todos"));

        // Link para buscar por setor
        model.add(linkTo(methodOn(ProdutoController.class).buscarPorSetor(produto.getSetor()))
                .withRel("setor"));

        // Link para atualizar (PUT)
        model.add(linkTo(methodOn(ProdutoController.class).atualizarProduto(produto.getId(), null))
                .withRel("atualizar"));

        // Link para deletar
        model.add(linkTo(methodOn(ProdutoController.class).deletarProduto(produto.getId()))
                .withRel("deletar"));

        return model;
    }
}
