package produtos;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String nome;
    private List<Produto> produtos;

    public Categoria(String nome) {
        this.nome = nome;
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    public void listarProdutos() {
        System.out.println("+==============================+");
        System.out.println("| " + nome.toUpperCase() + "                       |");
        System.out.println("+==============================+");
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            System.out.printf("| %d. %-24s |\n", (i + 1), produto.getNome() + " - R$ " + String.format("%.2f", produto.getPreco()));
        }
        System.out.println("+==============================+");
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}


