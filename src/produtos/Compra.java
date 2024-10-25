package produtos;

public class Compra {
    private String nomeProduto;
    private int quantidade;
    private double precoTotal;
    private String formaPagamento;
    private String codigoBarras;

    public Compra(String nomeProduto, int quantidade, double precoTotal, String formaPagamento, String codigoBarras) {
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoTotal = precoTotal;
        this.formaPagamento = formaPagamento;
        this.codigoBarras = codigoBarras;
    }

    @Override
    public String toString() {
        return String.format("Produto: %s | Quantidade: %d | Preço Total: R$ %.2f | Pagamento: %s | Código de Barras: %s",
                nomeProduto, quantidade, precoTotal, formaPagamento, codigoBarras);
    }
}


