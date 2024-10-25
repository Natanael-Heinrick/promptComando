package produtos;

public class Cliente {
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

