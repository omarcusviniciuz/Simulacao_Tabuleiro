import java.util.Scanner;
import model.Jogador;
import model.Imovel;
import model.ConfiguracaoPartida;
import controller.JogoController;

public class Main {
    private static Jogador[] jogadores = new Jogador[6];
    private static int qtdJogadores = 0;

    private static Imovel[] imoveis = new Imovel[40];
    private static int qtdImoveis = 0;

    private static ConfiguracaoPartida config = new ConfiguracaoPartida();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carregarDadosPadraoAutomatico(); // Facilita os testes iniciais

        int opcao = -1;
        while (opcao != 5) {
            System.out.println("\n========== JOGO DE TABULEIRO ESTRATÉGICO ==========");
            System.out.println("1. Gerenciar Jogadores (Cadastrados: " + qtdJogadores + ")");
            System.out.println("2. Gerenciar Imóveis (Cadastrados: " + qtdImoveis + ")");
            System.out.println("3. Configurar Partida");
            System.out.println("4. Iniciar Jogo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        menuJogadores();
                        break;
                    case 2:
                        menuImoveis();
                        break;
                    case 3:
                        menuConfiguracoes();
                        break;
                    case 4:
                        iniciarJogo();
                        break;
                    case 5:
                        System.out.println("Saindo... Obrigado por jogar!");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private static void menuJogadores() {
        int opcao = -1;
        while (opcao != 5) {
            System.out.println("\n--- GERENCIAR JOGADORES ---");
            System.out.println("1. Cadastrar Jogador");
            System.out.println("2. Listar Jogadores");
            System.out.println("3. Atualizar Jogador");
            System.out.println("4. Remover Jogador");
            System.out.println("5. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        cadastrarJogador();
                        break;
                    case 2:
                        listarJogadores();
                        break;
                    case 3:
                        atualizarJogador();
                        break;
                    case 4:
                        removerJogador();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private static void cadastrarJogador() {
        if (qtdJogadores >= 6) {
            System.out.println("Limite de jogadores (6) atingido!");
            return;
        }

        System.out.print("Digite o nome do jogador: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome não pode ser vazio!");
            return;
        }

        System.out.println("Selecione o Personagem:");
        System.out.println("1. ESPECULADOR (+20% salário por volta, +10% imposto)");
        System.out.println("2. NEGOCIANTE (paga 10% a menos de aluguel)");
        System.out.println("3. ADVOGADO (isenção de fiança na prisão)");
        System.out.println("4. CONSTRUTOR (imóveis que compra têm aluguel base +15%)");
        System.out.print("Escolha: ");

        String personagem = "";
        try {
            int p = Integer.parseInt(scanner.nextLine());
            switch (p) {
                case 1: personagem = "ESPECULADOR"; break;
                case 2: personagem = "NEGOCIANTE"; break;
                case 3: personagem = "ADVOGADO"; break;
                case 4: Math.max(1, 1); personagem = "CONSTRUTOR"; break;
                default:
                    System.out.println("Opção inválida! Usando padrão ESPECULADOR.");
                    personagem = "ESPECULADOR";
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida! Usando padrão ESPECULADOR.");
            personagem = "ESPECULADOR";
        }

        Jogador novo = new Jogador(nome, personagem);
        jogadores[qtdJogadores++] = novo;
        System.out.println("Jogador " + nome + " (" + personagem + ") cadastrado com sucesso!");
    }

    private static void listarJogadores() {
        if (qtdJogadores == 0) {
            System.out.println("Nenhum jogador cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE JOGADORES ---");
        for (int i = 0; i < qtdJogadores; i++) {
            System.out.println((i + 1) + ". Nome: " + jogadores[i].getNome() + " | Personagem: " + jogadores[i].getPersonagem());
        }
    }

    private static void atualizarJogador() {
        listarJogadores();
        if (qtdJogadores == 0) return;

        System.out.print("Escolha o número do jogador para atualizar: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= qtdJogadores) {
                System.out.println("Jogador inválido!");
                return;
            }
            System.out.print("Digite o novo nome: ");
            String nome = scanner.nextLine().trim();
            if (nome.isEmpty()) {
                System.out.println("Nome inválido!");
                return;
            }
            jogadores[indice].setNome(nome);
            System.out.println("Jogador atualizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
        }
    }

    private static void removerJogador() {
        listarJogadores();
        if (qtdJogadores == 0) return;

        System.out.print("Escolha o número do jogador para remover: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= qtdJogadores) {
                System.out.println("Jogador inválido!");
                return;
            }
            for (int i = indice; i < qtdJogadores - 1; i++) {
                jogadores[i] = jogadores[i + 1];
            }
            jogadores[qtdJogadores - 1] = null;
            qtdJogadores--;
            System.out.println("Jogador removido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
        }
    }

    private static void menuImoveis() {
        int opcao = -1;
        while (opcao != 6) {
            System.out.println("\n--- GERENCIAR IMÓVEIS ---");
            System.out.println("1. Cadastrar Imóvel");
            System.out.println("2. Listar Imóveis");
            System.out.println("3. Atualizar Imóvel");
            System.out.println("4. Remover Imóvel");
            System.out.println("5. Limpar e Carregar Cidades Brasileiras (Anexo A)");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        cadastrarImovel();
                        break;
                    case 2:
                        listarImoveis();
                        break;
                    case 3:
                        atualizarImovel();
                        break;
                    case 4:
                        removerImovel();
                        break;
                    case 5:
                        carregarDadosAnexoA();
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }
    }

    private static void cadastrarImovel() {
        if (qtdImoveis >= 40) {
            System.out.println("Limite de imóveis (40) atingido!");
            return;
        }

        System.out.print("Nome do imóvel: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome inválido!");
            return;
        }

        try {
            System.out.print("Preço de compra: ");
            double preco = Double.parseDouble(scanner.nextLine());
            System.out.print("Aluguel base: ");
            double aluguel = Double.parseDouble(scanner.nextLine());

            Imovel novo = new Imovel(nome, preco, aluguel);
            imoveis[qtdImoveis++] = novo;
            System.out.println("Imóvel " + nome + " cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Valores numéricos inválidos!");
        }
    }

    private static void listarImoveis() {
        if (qtdImoveis == 0) {
            System.out.println("Nenhum imóvel cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE IMÓVEIS ---");
        for (int i = 0; i < qtdImoveis; i++) {
            System.out.printf("%d. Nome: %s | Preço Compra: R$ %.2f | Aluguel Base: R$ %.2f%n",
                    (i + 1), imoveis[i].getNome(), imoveis[i].getPrecoCompra(), imoveis[i].getAluguelBase());
        }
    }

    private static void atualizarImovel() {
        listarImoveis();
        if (qtdImoveis == 0) return;

        System.out.print("Escolha o número do imóvel para atualizar: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= qtdImoveis) {
                System.out.println("Imóvel inválido!");
                return;
            }
            System.out.print("Digite o novo nome: ");
            String nome = scanner.nextLine().trim();
            System.out.print("Novo preço de compra: ");
            double preco = Double.parseDouble(scanner.nextLine());
            System.out.print("Novo aluguel base: ");
            double aluguel = Double.parseDouble(scanner.nextLine());

            imoveis[indice].setNome(nome);
            imoveis[indice].setPrecoCompra(preco);
            imoveis[indice].setAluguelBase(aluguel);
            System.out.println("Imóvel atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro na entrada de dados.");
        }
    }

    private static void removerImovel() {
        listarImoveis();
        if (qtdImoveis == 0) return;

        System.out.print("Escolha o número do imóvel para remover: ");
        try {
            int indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= qtdImoveis) {
                System.out.println("Imóvel inválido!");
                return;
            }
            for (int i = indice; i < qtdImoveis - 1; i++) {
                imoveis[i] = imoveis[i + 1];
            }
            imoveis[qtdImoveis - 1] = null;
            qtdImoveis--;
            System.out.println("Imóvel removido com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Número inválido.");
        }
    }

    private static void carregarDadosAnexoA() {
        // Limpa e carrega
        for (int i = 0; i < qtdImoveis; i++) {
            imoveis[i] = null;
        }
        qtdImoveis = 0;

        imoveis[qtdImoveis++] = new Imovel("Chalé da Serra Gaúcha", 200000.0, 1000.0);
        imoveis[qtdImoveis++] = new Imovel("Flat Paulista", 350000.0, 1750.0);
        imoveis[qtdImoveis++] = new Imovel("Sobrado de Ouro Preto", 400000.0, 2000.0);
        imoveis[qtdImoveis++] = new Imovel("Pousada do Pantanal", 500000.0, 2500.0);
        imoveis[qtdImoveis++] = new Imovel("Mansão de Gramado", 600000.0, 3000.0);
        imoveis[qtdImoveis++] = new Imovel("Cobertura de Florianópolis", 750000.0, 3750.0);
        imoveis[qtdImoveis++] = new Imovel("Fazenda do Cerrado", 280000.0, 1400.0);
        imoveis[qtdImoveis++] = new Imovel("Bangalô de Búzios", 450000.0, 2250.0);
        imoveis[qtdImoveis++] = new Imovel("Penthouse de Salvador", 850000.0, 4250.0);
        imoveis[qtdImoveis++] = new Imovel("Casa de Bonito", 220000.0, 1100.0);
        imoveis[qtdImoveis++] = new Imovel("Palacete de Petrópolis", 1000000.0, 5000.0);
        imoveis[qtdImoveis++] = new Imovel("Rancho do Vale do São Francisco", 310000.0, 1550.0);

        System.out.println("Imóveis do Anexo A carregados com sucesso (12 imóveis).");
    }

    private static void carregarDadosPadraoAutomatico() {
        // Pre-cadastra alguns jogadores para agilizar
        jogadores[qtdJogadores++] = new Jogador("Carlos", "ESPECULADOR");
        jogadores[qtdJogadores++] = new Jogador("Mariana", "CONSTRUTOR");
        
        // Carrega imóveis do anexo A
        carregarDadosAnexoA();
    }

    private static void menuConfiguracoes() {
        System.out.println("\n--- CONFIGURAR PARTIDA ---");
        System.out.printf("Saldo inicial atual: R$ %.2f%n", config.getSaldoInicial());
        System.out.printf("Salário atual: R$ %.2f%n", config.getSalario());
        System.out.printf("Número máximo de rodadas atual: %d%n", config.getMaxRodadas());

        try {
            System.out.print("Digite o novo saldo inicial (ou Enter para manter): ");
            String inputSaldo = scanner.nextLine().trim();
            if (!inputSaldo.isEmpty()) {
                config.setSaldoInicial(Double.parseDouble(inputSaldo));
            }

            System.out.print("Digite o novo salário (ou Enter para manter): ");
            String inputSalario = scanner.nextLine().trim();
            if (!inputSalario.isEmpty()) {
                config.setSalario(Double.parseDouble(inputSalario));
            }

            System.out.print("Digite o novo número máximo de rodadas (ou Enter para manter): ");
            String inputRodadas = scanner.nextLine().trim();
            if (!inputRodadas.isEmpty()) {
                config.setMaxRodadas(Integer.parseInt(inputRodadas));
            }

            System.out.println("Configurações atualizadas!");
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Mantendo valores anteriores.");
        }
    }

    private static void iniciarJogo() {
        if (qtdJogadores < 2) {
            System.out.println("Erro: São necessários pelo menos 2 jogadores cadastrados para iniciar.");
            return;
        }
        if (qtdImoveis < 10) {
            System.out.println("Erro: São necessários pelo menos 10 imóveis cadastrados para iniciar.");
            return;
        }

        // Criar cópia dos arrays com tamanho exato para passar ao controller
        Jogador[] partidaJogadores = new Jogador[qtdJogadores];
        System.arraycopy(jogadores, 0, partidaJogadores, 0, qtdJogadores);

        Imovel[] partidaImoveis = new Imovel[qtdImoveis];
        System.arraycopy(imoveis, 0, partidaImoveis, 0, qtdImoveis);

        JogoController controller = new JogoController(partidaJogadores, partidaImoveis, config);
        controller.iniciarPartida();
    }
}
