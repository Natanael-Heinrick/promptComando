package produtos;

public class Estoque {
    public static void verificarEstoque(Produto produto) {
        if (produto.getEstoque() < 10) {
            System.out.println("Atenção: O estoque de " + produto.getNome() + " está baixo!");
        }
    }
}

