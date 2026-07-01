package structure;

import model.Carta;
import java.util.Random;

public class Baralho {
    private PilhaCartas pilha;
    private Carta[] todasCartas;
    private Random random;

    public Baralho() {
        this.pilha = new PilhaCartas();
        this.random = new Random();
        inicializarBaseCartas();
        embaralharEReabastecer();
    }

    private void inicializarBaseCartas() {
        todasCartas = new Carta[] {
            // Cartas de Ganho / Avanço (6)
            new Carta("Sorte: Receba R$ 100.000 do banco.", "GANHO_DINHEIRO", 100000),
            new Carta("Sorte: Receba R$ 50.000 do banco.", "GANHO_DINHEIRO", 50000),
            new Carta("Sorte: Avance 3 casas.", "AVANCO", 3),
            new Carta("Sorte: Avance 5 casas.", "AVANCO", 5),
            new Carta("Sorte: Avance diretamente para o Início e receba seu salário.", "AVANCO_INICIO", 0),
            new Carta("Sorte: Todos os outros jogadores pagam R$ 20.000 a você.", "COBRAR_JOGADORES", 20000),

            // Cartas de Perda / Penalidade / Retrocesso (6)
            new Carta("Revés: Pague R$ 100.000 ao banco.", "PERDA_DINHEIRO", 100000),
            new Carta("Revés: Pague R$ 50.000 ao banco.", "PERDA_DINHEIRO", 50000),
            new Carta("Revés: Volte 3 casas.", "RETROCESSO", 3),
            new Carta("Revés: Volte 2 casas.", "RETROCESSO", 2),
            new Carta("Revés: Pague R$ 20.000 a cada um dos outros jogadores.", "PAGAR_JOGADORES", 20000),
            new Carta("Revés: Pague uma taxa ambiental de R$ 30.000 ao banco.", "PERDA_DINHEIRO", 30000)
        };
    }

    public void embaralharEReabastecer() {
        System.out.println("[BARALHO] O baralho esgotou ou está sendo inicializado. Remontando e embaralhando...");
        
        // Cópia das cartas originais
        Carta[] temp = new Carta[todasCartas.length];
        System.arraycopy(todasCartas, 0, temp, 0, todasCartas.length);

        // Algoritmo Fisher-Yates para embaralhamento
        for (int i = temp.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Carta swap = temp[i];
            temp[i] = temp[j];
            temp[j] = swap;
        }

        // Empilha as cartas embaralhadas
        for (Carta c : temp) {
            pilha.push(c);
        }
    }

    public Carta sacarCarta() {
        if (pilha.isEmpty()) {
            embaralharEReabastecer();
        }
        return pilha.pop();
    }
}
