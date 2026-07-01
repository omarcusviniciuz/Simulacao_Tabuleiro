package controller;

import model.ConfiguracaoPartida;
import model.Jogador;
import model.Imovel;
import model.Casa;
import model.Carta;
import structure.Tabuleiro;
import structure.NoTabuleiro;
import structure.Baralho;

import java.util.Random;
import java.util.Scanner;

public class JogoController {
    private Jogador[] jogadores;
    private Imovel[] imoveis;
    private ConfiguracaoPartida config;
    private Tabuleiro tabuleiro;
    private Baralho baralho;
    private Random random;
    private Scanner scanner;

    private int rodadaAtual;
    private double maiorAluguelCobrado;
    private String nomeMaiorAluguelCobrado;

    public JogoController(Jogador[] jogadores, Imovel[] imoveis, ConfiguracaoPartida config) {
        this.jogadores = jogadores;
        this.imoveis = imoveis;
        this.config = config;
        this.tabuleiro = new Tabuleiro();
        this.baralho = new Baralho();
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.rodadaAtual = 1;
        this.maiorAluguelCobrado = 0.0;
        this.nomeMaiorAluguelCobrado = "Nenhum";
    }

    public void iniciarPartida() {
        System.out.println("\n=============================================");
        System.out.println("          INICIANDO PARTIDA DE SIMULAÇÃO      ");
        System.out.println("=============================================");

        inicializarTabuleiro();
        inicializarJogadores();

        while (rodadaAtual <= config.getMaxRodadas() && contarJogadoresAtivos() > 1) {
            System.out.println("\n---------------------------------------------");
            System.out.println("              RODADA " + rodadaAtual + " DE " + config.getMaxRodadas());
            System.out.println("---------------------------------------------");

            for (Jogador j : jogadores) {
                if (j.isFalido()) {
                    continue;
                }
                processarTurno(j);
                if (contarJogadoresAtivos() <= 1) {
                    break;
                }
            }
            rodadaAtual++;
        }

        exibirRelatorioFinal();
    }

    private void inicializarTabuleiro() {
        // A primeira casa é sempre o INICIO
        tabuleiro.adicionarCasa(new Casa("Início", "INICIO"));

        // Intercalação das propriedades com casas especiais
        int indImovel = 0;
        int cont = 1;
        while (indImovel < imoveis.length) {
            tabuleiro.adicionarCasa(imoveis[indImovel++]);

            if (cont % 3 == 0) {
                tabuleiro.adicionarCasa(new Casa("Sorte/Revés", "SORTE_REVES"));
            }
            if (cont % 5 == 0) {
                tabuleiro.adicionarCasa(new Casa("Imposto", "IMPOSTO"));
            }
            if (cont % 7 == 0) {
                tabuleiro.adicionarCasa(new Casa("Restituição", "RESTITUICAO"));
            }
            cont++;
        }

        System.out.println("[TABULEIRO] Tabuleiro criado com " + tabuleiro.getTotalCasas() + " casas.");
        exibirTabuleiroDetalhados();
    }

    private void exibirTabuleiroDetalhados() {
        System.out.println("\n--- CASAS DO TABULEIRO ---");
        NoTabuleiro atual = tabuleiro.getHead();
        for (int i = 0; i < tabuleiro.getTotalCasas(); i++) {
            System.out.println("- Casa " + (i + 1) + ": " + atual.getCasa().getNome() + " [" + atual.getCasa().getTipo() + "]");
            atual = atual.getNext();
        }
    }

    private void inicializarJogadores() {
        NoTabuleiro inicio = tabuleiro.getHead();
        for (Jogador j : jogadores) {
            j.setSaldo(config.getSaldoInicial());
            j.setPosicaoAtual(inicio);
            j.setVoltasCompletas(0);
            j.setFalido(false);
        }
    }

    private int contarJogadoresAtivos() {
        int cont = 0;
        for (Jogador j : jogadores) {
            if (!j.isFalido()) {
                cont++;
            }
        }
        return cont;
    }

    private void processarTurno(Jogador j) {
        System.out.println("\nTurno do jogador: " + j.getNome() + " (" + j.getPersonagem() + ")");
        System.out.printf("Saldo Atual: R$ %.2f%n", j.getSaldo());
        System.out.println("Pressione Enter para lançar os dados...");
        scanner.nextLine();

        int d1 = random.nextInt(6) + 1;
        int d2 = random.nextInt(6) + 1;
        int totalDados = d1 + d2;
        System.out.println("Resultado dos dados: " + d1 + " + " + d2 + " = " + totalDados);

        moverJogador(j, totalDados, true);
    }

    private void moverJogador(Jogador j, int passos, boolean paraFrente) {
        NoTabuleiro atual = j.getPosicaoAtual();
        System.out.println("Saindo de: " + atual.getCasa().getNome());

        for (int i = 0; i < passos; i++) {
            if (paraFrente) {
                atual = atual.getNext();
                // Detecção de passagem pelo Início
                if (atual.getCasa().getTipo().equals("INICIO")) {
                    double valorSalario = config.getSalario();
                    if ("ESPECULADOR".equalsIgnoreCase(j.getPersonagem())) {
                        valorSalario *= 1.20; // Bônus do Especulador
                        System.out.println("[HABILIDADE] Passiva do Especulador ativada: +20% de salário!");
                    }
                    j.adicionarSaldo(valorSalario);
                    j.incrementarVoltas();
                    System.out.printf("[INICIO] %s passou pelo início! Recebeu salário. Novo Saldo: R$ %.2f%n", j.getNome(), j.getSaldo());
                }
            } else {
                atual = atual.getPrev();
                // Detecção de passagem pelo Início ao retroceder (Sem salário)
                if (atual.getCasa().getTipo().equals("INICIO")) {
                    System.out.println("[INICIO] " + j.getNome() + " passou pelo início voltando. Sem recebimento de salário.");
                }
            }
        }

        j.setPosicaoAtual(atual);
        System.out.println("Parou em: " + atual.getCasa().getNome() + " (" + atual.getCasa().getTipo() + ")");
        
        // Registrar visita no caso de ser Imóvel
        if (atual.getCasa() instanceof Imovel) {
            Imovel im = (Imovel) atual.getCasa();
            im.registrarVisita();
        }

        aplicarEfeitoCasa(j, atual.getCasa());
        verificarFalencia(j);
    }

    private void aplicarEfeitoCasa(Jogador j, Casa casa) {
        switch (casa.getTipo()) {
            case "IMOVEL":
                processarImovel(j, (Imovel) casa);
                break;
            case "IMPOSTO":
                processarImposto(j);
                break;
            case "RESTITUICAO":
                processarRestituicao(j);
                break;
            case "SORTE_REVES":
                processarSorteReves(j);
                break;
            default:
                break;
        }
    }

    private void processarImovel(Jogador j, Imovel im) {
        if (im.getDono() == null) {
            // Imóvel sem dono - Tentativa de Compra
            if (j.getSaldo() >= im.getPrecoCompra()) {
                System.out.printf("Este imóvel não tem dono. Deseja comprá-lo por R$ %.2f? (S/N): ", im.getPrecoCompra());
                String resp = scanner.nextLine().trim();
                if ("S".equalsIgnoreCase(resp)) {
                    j.descontarSaldo(im.getPrecoCompra());
                    im.setDono(j);
                    j.adicionarPropriedade(im);
                    System.out.printf("[COMPRA] %s comprou %s! Novo Saldo: R$ %.2f%n", j.getNome(), im.getNome(), j.getSaldo());
                } else {
                    System.out.println("Compra recusada.");
                }
            } else {
                System.out.printf("Você não tem saldo suficiente para comprar este imóvel (Preço: R$ %.2f).%n", im.getPrecoCompra());
            }
        } else if (im.getDono() == j) {
            System.out.println("Você parou na sua própria propriedade.");
        } else {
            // Pagar aluguel
            double aluguel = im.calcularAluguelAtual();
            
            // Habilidade do Negociante: paga 10% a menos
            if ("NEGOCIANTE".equalsIgnoreCase(j.getPersonagem())) {
                aluguel *= 0.90;
                System.out.println("[HABILIDADE] Passiva do Negociante ativada: -10% de desconto no aluguel!");
            }

            System.out.printf("[ALUGUEL] %s parou em imóvel de %s. Aluguel cobrado: R$ %.2f (Valorização atual: %.1fx)%n",
                    j.getNome(), im.getDono().getNome(), aluguel, im.getMultiplicadorValorizacao());

            j.descontarSaldo(aluguel);
            im.getDono().adicionarSaldo(aluguel);

            // Registro do maior aluguel cobrado
            if (aluguel > maiorAluguelCobrado) {
                maiorAluguelCobrado = aluguel;
                nomeMaiorAluguelCobrado = im.getNome();
            }

            System.out.printf("Saldo %s: R$ %.2f | Saldo %s: R$ %.2f%n",
                    j.getNome(), j.getSaldo(), im.getDono().getNome(), im.getDono().getSaldo());
        }
    }

    private void processarImposto(Jogador j) {
        double patrimonio = j.obterPatrimonioTotal();
        double imposto = patrimonio * 0.05;

        // Especulador paga +10% de imposto
        if ("ESPECULADOR".equalsIgnoreCase(j.getPersonagem())) {
            imposto *= 1.10;
            System.out.println("[HABILIDADE] Passiva do Especulador ativada: +10% de taxa sobre o imposto!");
        }

        System.out.printf("[IMPOSTO] %s parou na casa de imposto. Taxa cobrada: R$ %.2f (5%% do patrimônio R$ %.2f)%n",
                j.getNome(), imposto, patrimonio);

        j.descontarSaldo(imposto);
        System.out.printf("Novo Saldo %s: R$ %.2f%n", j.getNome(), j.getSaldo());
    }

    private void processarRestituicao(Jogador j) {
        double restitricao = config.getSalario() * 0.10;
        System.out.printf("[RESTITUIÇÃO] %s parou na casa de restituição. Recebeu R$ %.2f (10%% do salário)%n",
                j.getNome(), restitricao);
        j.adicionarSaldo(restitricao);
        System.out.printf("Novo Saldo %s: R$ %.2f%n", j.getNome(), j.getSaldo());
    }

    private void processarSorteReves(Jogador j) {
        Carta carta = baralho.sacarCarta();
        System.out.println("[SORTE/REVÉS] Carta sacada: " + carta.getDescricao());

        switch (carta.getTipo()) {
            case "GANHO_DINHEIRO":
                j.adicionarSaldo(carta.getValor());
                System.out.printf("%s ganhou R$ %.2f. Novo Saldo: R$ %.2f%n", j.getNome(), (double) carta.getValor(), j.getSaldo());
                break;
            case "PERDA_DINHEIRO":
                j.descontarSaldo(carta.getValor());
                System.out.printf("%s pagou R$ %.2f. Novo Saldo: R$ %.2f%n", j.getNome(), (double) carta.getValor(), j.getSaldo());
                break;
            case "AVANCO":
                System.out.println("Avançando " + carta.getValor() + " casas...");
                moverJogador(j, carta.getValor(), true);
                break;
            case "RETROCESSO":
                System.out.println("Voltando " + carta.getValor() + " casas...");
                moverJogador(j, carta.getValor(), false);
                break;
            case "AVANCO_INICIO":
                NoTabuleiro inicio = tabuleiro.getHead();
                // Move o jogador diretamente para o Início
                j.setPosicaoAtual(inicio);
                double valorSalario = config.getSalario();
                if ("ESPECULADOR".equalsIgnoreCase(j.getPersonagem())) {
                    valorSalario *= 1.20;
                    System.out.println("[HABILIDADE] Passiva do Especulador ativada: +20% de salário!");
                }
                j.adicionarSaldo(valorSalario);
                j.incrementarVoltas();
                System.out.printf("[INICIO] %s avançou diretamente para o início e recebeu salário! Novo Saldo: R$ %.2f%n", j.getNome(), j.getSaldo());
                aplicarEfeitoCasa(j, inicio.getCasa());
                break;
            case "COBRAR_JOGADORES":
                int cobrados = 0;
                for (Jogador outro : jogadores) {
                    if (outro != j && !outro.isFalido()) {
                        outro.descontarSaldo(carta.getValor());
                        j.adicionarSaldo(carta.getValor());
                        cobrados++;
                        verificarFalencia(outro);
                    }
                }
                System.out.println("Cobrou R$ " + carta.getValor() + " de " + cobrados + " jogadores ativos.");
                break;
            case "PAGAR_JOGADORES":
                int pagos = 0;
                for (Jogador outro : jogadores) {
                    if (outro != j && !outro.isFalido()) {
                        j.descontarSaldo(carta.getValor());
                        outro.adicionarSaldo(carta.getValor());
                        pagos++;
                    }
                }
                System.out.println("Pagou R$ " + carta.getValor() + " para " + pagos + " jogadores ativos.");
                break;
            default:
                break;
        }
    }

    private void verificarFalencia(Jogador j) {
        if (j.getSaldo() < 0 && j.getQtdPropriedades() == 0) {
            j.setFalido(true);
            j.limparPropriedades();
            System.out.println("\n[FALÊNCIA] " + j.getNome() + " FALIU e foi removido do jogo!");
        }
    }

    public void exibirRelatorioFinal() {
        System.out.println("\n=============================================");
        System.out.println("              FIM DE JOGO                    ");
        System.out.println("=============================================");

        // Ordenar jogadores por patrimônio (Bubble sort simples para evitar importações)
        Jogador[] ordenados = new Jogador[jogadores.length];
        System.arraycopy(jogadores, 0, ordenados, 0, jogadores.length);

        for (int i = 0; i < ordenados.length - 1; i++) {
            for (int j = 0; j < ordenados.length - i - 1; j++) {
                if (ordenados[j].obterPatrimonioTotal() < ordenados[j + 1].obterPatrimonioTotal()) {
                    Jogador swap = ordenados[j];
                    ordenados[j] = ordenados[j + 1];
                    ordenados[j + 1] = swap;
                }
            }
        }

        System.out.println("\n--- CLASSIFICAÇÃO DOS JOGADORES (PATRIMÔNIO) ---");
        for (int i = 0; i < ordenados.length; i++) {
            Jogador jog = ordenados[i];
            String status = jog.isFalido() ? "[FALIDO]" : "[ATIVO]";
            System.out.printf("%d. %s - Patrimônio Total: R$ %.2f | Saldo: R$ %.2f | Voltas Completas: %d %s%n",
                    (i + 1), jog.getNome(), jog.obterPatrimonioTotal(), jog.getSaldo(), jog.getVoltasCompletas(), status);
        }

        System.out.println("\n--- ESTATÍSTICAS ---");
        System.out.printf("Imóvel com maior aluguel cobrado: %s | Valor: R$ %.2f%n", nomeMaiorAluguelCobrado, maiorAluguelCobrado);
        System.out.println("=============================================");
    }
}
