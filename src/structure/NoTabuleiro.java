package structure;

import model.Casa;

public class NoTabuleiro {
    private Casa casa;
    private NoTabuleiro next;
    private NoTabuleiro prev;

    public NoTabuleiro(Casa casa) {
        this.casa = casa;
        this.next = null;
        this.prev = null;
    }

    public Casa getCasa() {
        return casa;
    }

    public NoTabuleiro getNext() {
        return next;
    }

    public void setNext(NoTabuleiro next) {
        this.next = next;
    }

    public NoTabuleiro getPrev() {
        return prev;
    }

    public void setPrev(NoTabuleiro prev) {
        this.prev = prev;
    }
}
