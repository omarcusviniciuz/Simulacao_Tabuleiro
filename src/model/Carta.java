package model;

public class Carta {
    private String descricao;
    private String tipo; // GANHO_DINHEIRO, PERDA_DINHEIRO, AVANCO, RETROCESSO
    private int valor;   // quantidade de dinheiro ou de casas

    public Carta(String descricao, String tipo, int valor) {
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public int getValor() {
        return valor;
    }
}
