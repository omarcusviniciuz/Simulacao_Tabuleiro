package model;

public class ConfiguracaoPartida {
    private double saldoInicial;
    private double salario;
    private int maxRodadas;

    public ConfiguracaoPartida() {
        this.saldoInicial = 1500000.0; // Padrão
        this.salario = 200000.0;       // Padrão
        this.maxRodadas = 30;          // Padrão
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getMaxRodadas() {
        return maxRodadas;
    }

    public void setMaxRodadas(int maxRodadas) {
        this.maxRodadas = maxRodadas;
    }
}
