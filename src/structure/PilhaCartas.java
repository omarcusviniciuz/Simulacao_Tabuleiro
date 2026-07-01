package structure;

import model.Carta;

public class PilhaCartas {
    private NoCarta topo;
    private int tamanho;

    public PilhaCartas() {
        this.topo = null;
        this.tamanho = 0;
    }

    public void push(Carta carta) {
        NoCarta novo = new NoCarta(carta);
        novo.setNext(topo);
        topo = novo;
        tamanho++;
    }

    public Carta pop() {
        if (isEmpty()) {
            return null;
        }
        Carta carta = topo.getCarta();
        topo = topo.getNext();
        tamanho--;
        return carta;
    }

    public boolean isEmpty() {
        return topo == null;
    }

    public int getTamanho() {
        return tamanho;
    }
}
