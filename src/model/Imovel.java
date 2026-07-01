package model;

public class Imovel extends Casa {
    private double precoCompra;
    private double aluguelBase;
    private double multiplicadorValorizacao;
    private Jogador dono;

    public Imovel(String nome, double precoCompra, double aluguelBase) {
        super(nome, "IMOVEL");
        this.precoCompra = precoCompra;
        this.aluguelBase = aluguelBase;
        this.multiplicadorValorizacao = 1.0;
        this.dono = null;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getAluguelBase() {
        return aluguelBase;
    }

    public void setAluguelBase(double aluguelBase) {
        this.aluguelBase = aluguelBase;
    }

    public double getMultiplicadorValorizacao() {
        return multiplicadorValorizacao;
    }

    public void registrarVisita() {
        if (multiplicadorValorizacao < 2.0) {
            multiplicadorValorizacao += 0.1;
            if (multiplicadorValorizacao > 2.0) {
                multiplicadorValorizacao = 2.0;
            }
        }
    }

    public double calcularAluguelAtual() {
        double base = aluguelBase;
        if (dono != null && "CONSTRUTOR".equalsIgnoreCase(dono.getPersonagem())) {
            base = aluguelBase * 1.15;
        }
        return base * multiplicadorValorizacao;
    }

    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }

    public void resetarPropriedade() {
        this.dono = null;
        this.multiplicadorValorizacao = 1.0;
    }
}
