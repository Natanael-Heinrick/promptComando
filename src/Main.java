import produtos.Categoria;
import produtos.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Cliente {
    private String nome;
    private String email;
    private int pontos;

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.pontos = 0;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getPontos() {
        return pontos;
    }

    public void adicionarPontos(int pontos) {
        this.pontos += pontos;
    }

    public void usarPontos(int pontos) {
        this.pontos -= pontos;
    }
}

class Compra {
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

public class Main {
    private static List<Compra> historicoCompras = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static Cliente clienteLogado;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicializa as categorias e produtos
        Categoria supermercado = criarSupermercado();
        Categoria eletronicos = criarEletronicos();

        while (true) {
            // Menu principal
            System.out.println("+==============================+");
            System.out.println("|  1 - CADASTRAR CLIENTE       |");
            System.out.println("|  2 - LOGIN                   |");
            System.out.println("|  3 - SAIR                    |");
            System.out.println("+==============================+");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (opcao == 1) {
                // Cadastro de cliente
                System.out.println("Nome:");
                String nome = scanner.nextLine();
                System.out.println("E-mail:");
                String email = scanner.nextLine();
                clientes.add(new Cliente(nome, email));
                System.out.println("Cadastro realizado com sucesso!");
            } else if (opcao == 2) {
                // Login
                System.out.println("Digite seu e-mail:");
                String email = scanner.nextLine();
                clienteLogado = buscarClientePorEmail(email);
                if (clienteLogado != null) {
                    System.out.println("Login realizado com sucesso! Bem-vindo, " + clienteLogado.getNome());
                    realizarCompras(scanner, supermercado, eletronicos);
                } else {
                    System.out.println("Cliente não encontrado. Verifique seu e-mail.");
                }
            } else if (opcao == 3) {
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }

        scanner.close();
    }

    private static void realizarCompras(Scanner scanner, Categoria supermercado, Categoria eletronicos) {
        boolean continuarComprando = true;

        while (continuarComprando) {
            // Menu de opções
            System.out.println("+==============================+");
            System.out.println("|  ESCOLHA UMA OPÇÃO            |");
            System.out.println("+==============================+");
            System.out.println("|  1 - Supermercado            |");
            System.out.println("|  2 - Eletrônicos             |");
            System.out.println("|  3 - Histórico de Compras     |");
            System.out.println("+==============================+");

            int escolha = scanner.nextInt();
            Categoria categoriaEscolhida = (escolha == 1) ? supermercado : (escolha == 2) ? eletronicos : null;

            if (escolha == 3) {
                mostrarHistorico();
                continue;
            }

            if (categoriaEscolhida != null) {
                boolean continuarNaLoja = true;

                while (continuarNaLoja) {
                    categoriaEscolhida.listarProdutos();
                    System.out.println("Escolha o número do produto que deseja comprar:");
                    int produtoEscolhido = scanner.nextInt() - 1; // Ajusta para zero-based index

                    if (produtoEscolhido >= 0 && produtoEscolhido < categoriaEscolhida.getProdutos().size()) {
                        Produto produto = categoriaEscolhida.getProdutos().get(produtoEscolhido);
                        System.out.println("Quantas unidades você deseja?");
                        int quantidade = scanner.nextInt();

                        if (quantidade <= produto.getEstoque()) {
                            double precoTotal = produto.calcularPrecoTotal(quantidade);

                            // Aplicar pontos
                            if (clienteLogado.getPontos() > 0) {
                                System.out.println("Você tem " + clienteLogado.getPontos() + " pontos. Deseja usar pontos para desconto? (1 - Sim, 2 - Não)");
                                int usarPontos = scanner.nextInt();
                                if (usarPontos == 1) {
                                    System.out.println("Quantos pontos deseja usar? (Máximo: " + clienteLogado.getPontos() + ")");
                                    int pontosUsar = scanner.nextInt();
                                    if (pontosUsar <= clienteLogado.getPontos()) {
                                        precoTotal -= pontosUsar; // Aplicando desconto
                                        clienteLogado.usarPontos(pontosUsar);
                                    } else {
                                        System.out.println("Pontos insuficientes.");
                                    }
                                }
                            }

                            System.out.println("Preço total: R$ " + String.format("%.2f", precoTotal));

                            // Opções de pagamento
                            System.out.println("+==============================+");
                            System.out.println("|  ESCOLHA A FORMA DE PAGAMENTO |");
                            System.out.println("+==============================+");
                            System.out.println("|  1 - PIX (10% de desconto)   |");
                            System.out.println("|  2 - Cartão de Crédito        |");
                            System.out.println("+==============================+");
                            int formaPagamento = scanner.nextInt();

                            double precoFinal = precoTotal;
                            if (formaPagamento == 1) {
                                double desconto = precoTotal * 0.10;
                                precoFinal = precoTotal - desconto;
                                System.out.println("Total com desconto: R$ " + String.format("%.2f", precoFinal));
                            } else if (formaPagamento == 2) {
                                System.out.println("Total a pagar: R$ " + String.format("%.2f", precoTotal));
                            } else {
                                System.out.println("Opção de pagamento inválida!");
                                continue; // Retorna ao início da compra
                            }

                            produto.vender(quantidade); // Atualiza estoque
                            System.out.println("Compra realizada com sucesso!");

                            // Gerar e exibir o código de barras
                            String barcode = generateBarcode(produto.getNome().length() + "" + quantidade);
                            gerarRecibo(produto, quantidade, precoFinal, formaPagamento, barcode);

                            // Adiciona a compra ao histórico
                            String formaPagamentoTexto = (formaPagamento == 1) ? "PIX (10% desconto)" : "Cartão de Crédito";
                            historicoCompras.add(new Compra(produto.getNome(), quantidade, precoFinal, formaPagamentoTexto, barcode));
                            clienteLogado.adicionarPontos((int) precoFinal); // Adiciona pontos equivalentes ao valor da compra

                            // Pergunta se deseja continuar na loja
                            System.out.println("Deseja fazer outra compra? (1 - Sim, 2 - Não)");
                            int resposta = scanner.nextInt();
                            continuarNaLoja = (resposta == 1);
                        } else {
                            System.out.println("Estoque insuficiente para " + quantidade + " unidades de " + produto.getNome());
                        }
                    } else {
                        System.out.println("Produto inválido!");
                    }
                }
            } else {
                System.out.println("Opção inválida!");
            }

            // Pergunta se deseja entrar em outra loja
            System.out.println("Deseja entrar em outra loja? (1 - Sim, 2 - Não)");
            int respostaLoja = scanner.nextInt();
            continuarComprando = (respostaLoja == 1);
        }
    }

    private static void mostrarHistorico() {
        System.out.println("+==============================+");
        System.out.println("|        HISTÓRICO DE COMPRAS   |");
        System.out.println("+==============================+");
        if (historicoCompras.isEmpty()) {
            System.out.println("Nenhuma compra registrada.");
        } else {
            for (Compra compra : historicoCompras) {
                System.out.println(compra);
            }
        }
        System.out.println("+==============================+");
    }

    private static Cliente buscarClientePorEmail(String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equals(email)) {
                return cliente;
            }
        }
        return null;
    }

    private static Categoria criarSupermercado() {
        Categoria supermercado = new Categoria("Supermercado");
        supermercado.adicionarProduto(new Produto("Arroz branco", 5.99, 100));
        supermercado.adicionarProduto(new Produto("Feijão carioca", 4.50, 100));
        supermercado.adicionarProduto(new Produto("Leite integral", 3.89, 100));
        supermercado.adicionarProduto(new Produto("Óleo de soja", 8.99, 100));
        supermercado.adicionarProduto(new Produto("Macarrão espaguete", 2.99, 100));
        supermercado.adicionarProduto(new Produto("Batata", 3.49, 100));
        supermercado.adicionarProduto(new Produto("Tomate", 4.99, 100));
        supermercado.adicionarProduto(new Produto("Cebola", 2.99, 100));
        supermercado.adicionarProduto(new Produto("Banana", 4.99, 100));
        supermercado.adicionarProduto(new Produto("Sabonete em barra", 2.49, 100));
        return supermercado;
    }

    private static Categoria criarEletronicos() {
        Categoria eletronicos = new Categoria("Eletrônicos");
        eletronicos.adicionarProduto(new Produto("Carregador de celular", 19.99, 100));
        eletronicos.adicionarProduto(new Produto("Fone de ouvido (p2)", 9.99, 100));
        eletronicos.adicionarProduto(new Produto("Pilhas alcalinas", 6.99, 100));
        eletronicos.adicionarProduto(new Produto("Controle remoto universal", 24.99, 100));
        eletronicos.adicionarProduto(new Produto("Pen drive 8GB", 14.99, 100));
        eletronicos.adicionarProduto(new Produto("Rádio portátil", 39.99, 100));
        eletronicos.adicionarProduto(new Produto("Calculadora simples", 9.99, 100));
        eletronicos.adicionarProduto(new Produto("Relógio digital", 29.99, 100));
        eletronicos.adicionarProduto(new Produto("Câmera digital compacta", 199.99, 100));
        eletronicos.adicionarProduto(new Produto("Mouse sem fio", 34.99, 100));
        return eletronicos;
    }

    public static String generateBarcode(String input) {
        StringBuilder barcode = new StringBuilder();

        for (char c : input.toCharArray()) {
            int number = Character.getNumericValue(c);
            String representation = createBarRepresentation(number);
            barcode.append(representation).append(" ");
        }

        return barcode.toString().trim();
    }

    private static String createBarRepresentation(int number) {
        switch (number) {
            case 0: return "||";
            case 1: return "|||";
            case 2: return "||||";
            case 3: return "|||||";
            case 4: return "| |||";
            case 5: return "| ||||";
            case 6: return "|| |||";
            case 7: return "||| |||";
            case 8: return "|| ||||";
            case 9: return "|||| |||";
            default: return "";
        }
    }

    private static void gerarRecibo(Produto produto, int quantidade, double precoTotal, int formaPagamento, String codigoBarras) {
        String formaPagamentoTexto = (formaPagamento == 1) ? "PIX (10% desconto)" : "Cartão de Crédito";
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






