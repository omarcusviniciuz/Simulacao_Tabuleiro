package structure;

import model.Casa;

public class Tabuleiro {
    private NoTabuleiro head;
    private NoTabuleiro tail;
    private int totalCasas;

    public Tabuleiro() {
        this.head = null;
        this.tail = null;
        this.totalCasas = 0;
    }

    public void adicionarCasa(Casa casa) {
        NoTabuleiro novo = new NoTabuleiro(casa);
        if (head == null) {
            head = novo;
            tail = novo;
            novo.setNext(novo);
            novo.setPrev(novo);
        } else {
            tail.setNext(novo);
            novo.setPrev(tail);
            novo.setNext(head);
            head.setPrev(novo);
            tail = novo;
        }
        totalCasas++;
    }

    public NoTabuleiro getHead() {
        return head;
    }

    public int getTotalCasas() {
        return totalCasas;
    }
}
