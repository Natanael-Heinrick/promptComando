package produtos;

public class Produto {
    private String nome;
    private double preco;
    private int estoque;

    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void vender(int quantidade) {
        if (quantidade <= estoque) {
            estoque -= quantidade;
        } else {
            System.out.println("Estoque insuficiente para " + nome);
        }
    }

    public double calcularPrecoTotal(int quantidade) {
        return preco * quantidade;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco) + " (Estoque: " + estoque + ")";
    }
}



