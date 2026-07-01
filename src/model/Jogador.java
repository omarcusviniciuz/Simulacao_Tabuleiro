package model;

import structure.NoTabuleiro;

public class Jogador {
    private String nome;
    private double saldo;
    private NoTabuleiro posicaoAtual;
    private String personagem; // ESPECULADOR, NEGOCIANTE, ADVOGADO, CONSTRUTOR
    private int voltasCompletas;
    private boolean falido;

    // Gerenciamento manual de propriedades usando array redimensionável simples
    private Imovel[] propriedades;
    private int qtdPropriedades;

    public Jogador(String nome, String personagem) {
        this.nome = nome;
        this.personagem = personagem;
        this.saldo = 0;
        this.posicaoAtual = null;
        this.voltasCompletas = 0;
        this.falido = false;
        this.propriedades = new Imovel[10];
        this.qtdPropriedades = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    public void descontarSaldo(double valor) {
        this.saldo -= valor;
    }

    public NoTabuleiro getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(NoTabuleiro posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public String getPersonagem() {
        return personagem;
    }

    public void setPersonagem(String personagem) {
        this.personagem = personagem;
    }

    public int getVoltasCompletas() {
        return voltasCompletas;
    }

    public void setVoltasCompletas(int voltasCompletas) {
        this.voltasCompletas = voltasCompletas;
    }

    public void incrementarVoltas() {
        this.voltasCompletas++;
    }

    public boolean isFalido() {
        return falido;
    }

    public void setFalido(boolean falido) {
        this.falido = falido;
    }

    public Imovel[] getPropriedades() {
        // Retorna um array com tamanho exato para facilitar iteração
        Imovel[] resultado = new Imovel[qtdPropriedades];
        System.arraycopy(propriedades, 0, resultado, 0, qtdPropriedades);
        return resultado;
    }

    public void adicionarPropriedade(Imovel imovel) {
        if (qtdPropriedades == propriedades.length) {
            // Redimensiona o array
            Imovel[] novo = new Imovel[propriedades.length * 2];
            System.arraycopy(propriedades, 0, novo, 0, propriedades.length);
            propriedades = novo;
        }
        propriedades[qtdPropriedades++] = imovel;
    }

    public void limparPropriedades() {
        for (int i = 0; i < qtdPropriedades; i++) {
            propriedades[i].resetarPropriedade();
            propriedades[i] = null;
        }
        qtdPropriedades = 0;
    }

    public double obterPatrimonioTotal() {
        double total = saldo;
        for (int i = 0; i < qtdPropriedades; i++) {
            total += propriedades[i].getPrecoCompra();
        }
        return total;
    }

    public int getQtdPropriedades() {
        return qtdPropriedades;
    }
}
