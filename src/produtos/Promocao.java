package produtos;

public class Promocao {
    private Produto produto;
    private double desconto;
    private String diaPromocional;

    public Promocao(Produto produto, double desconto, String diaPromocional) {
        this.produto = produto;
        this.desconto = desconto;
        this.diaPromocional = diaPromocional;
    }

    public Produto getProduto() {
        return produto;
    }

    public double getDesconto() {
        return desconto;
    }

    public String getDiaPromocional() {
        return diaPromocional;
    }
}

