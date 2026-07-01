package structure;

import model.Carta;

public class NoCarta {
    private Carta carta;
    private NoCarta next;

    public NoCarta(Carta carta) {
        this.carta = carta;
        this.next = null;
    }

    public Carta getCarta() {
        return carta;
    }

    public NoCarta getNext() {
        return next;
    }

    public void setNext(NoCarta next) {
        this.next = next;
    }
}
