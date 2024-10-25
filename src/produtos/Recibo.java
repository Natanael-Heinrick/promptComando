package produtos;

public class Recibo {
    public static void gerarRecibo(Produto produto, int quantidade, double precoTotal, String formaPagamento, String codigoBarras) {
        String formaPagamentoTexto = formaPagamento.equals("1") ? "PIX (10% desconto)" : "Cartão de Crédito";
        System.out.println("+==============================+");
        System.out.println("|            RECIBO            |");
        System.out.println("+==============================+");
        System.out.println("Produto: " + produto.getNome());
        System.out.println("Quantidade: " + quantidade);
        System.out.println("Preço Total: R$ " + String.format("%.2f", precoTotal));
        System.out.println("Forma de Pagamento: " + formaPagamentoTexto);
        System.out.println("Código de Barras: " + codigoBarras);
        System.out.println("+==============================+");
    }
}

