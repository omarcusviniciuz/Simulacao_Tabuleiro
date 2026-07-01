# Simulador de Jogo de Tabuleiro Estratégico

Este projeto é uma aplicação Java desenvolvida para console que simula um jogo de tabuleiro de gestão imobiliária, focado na aplicação prática de estruturas de dados lineares personalizadas.

O projeto foi construído respeitando as regras de **Trabalho Individual**, com remoção total das mecânicas de Prisão, Leilão e Histórico de Rodadas.

---

## 🛠️ Decisões de Modelagem e Estruturas de Dados

Para atender às restrições acadêmicas, nenhuma coleção da biblioteca padrão do Java (`java.util.LinkedList`, `java.util.Stack`, `java.util.Queue`, `ArrayList`, etc.) foi usada para gerenciar as mecânicas centrais.

### 1. Tabuleiro (Lista Duplamente Ligada Circular)
* **Decisão**: Criada a estrutura manual utilizando as classes `NoTabuleiro` e `Tabuleiro`.
* **Justificativa**: Cada casa possui referências para a próxima (`next`) e anterior (`prev`). A última casa se conecta à primeira, formando o ciclo. Isso viabiliza a navegação fluida em avanço regular e retrocesso (quando ativado por cartas de Revés), permitindo caminhar no sentido horário ou anti-horário com complexidade $O(1)$ por passo.

### 2. Baralho (Pilha Encadeada Manual)
* **Decisão**: Implementada a estrutura LIFO (Last-In, First-Out) nas classes `NoCarta` e `PilhaCartas`.
* **Justificativa**: O baralho de cartas de Sorte/Revés exige que apenas a carta do topo seja sacada. 
* **Embaralhamento**: Quando a pilha se esgota, o sistema coleta as 12 cartas padrão em um array temporário, aplica o algoritmo **Fisher-Yates** (que garante permutação aleatória uniforme) e as empilha novamente.

### 3. Gerenciamento de Coleções Auxiliares
* **Decisão**: Arrays simples (`Jogador[]` e `Imovel[]`) foram usados para armazenar os cadastros de jogadores e propriedades.
* **Justificativa**: Evita o uso de coleções padrão do Java e respeita com simplicidade os limites do enunciado (mínimo de 2 e máximo de 6 jogadores; mínimo de 10 e máximo de 40 imóveis).

---

## 👥 Personagens e Regras de Negócio

Cada jogador escolhe um personagem com habilidades passivas ativas no jogo:
* **Especulador**: Recebe bônus de **+20% no salário** ao completar voltas no início, mas paga **+10% a mais de imposto** nas casas de Imposto.
* **Negociante**: Paga **10% a menos de aluguel** ao parar nas propriedades dos adversários.
* **Construtor**: Imóveis que o Construtor compra ganham um bônus definitivo de **+15% no aluguel base**.
* **Advogado**: Personagem cadastrável conforme o enunciado original (embora sem efeito ativo prático, dado que a Prisão foi removida da versão de entrega individual).

### 🏠 Dinâmica do Tabuleiro
* **Passagem pelo Início**: O jogador recebe o salário configurado sempre que ultrapassa ou para na casa de Início indo para a frente. Movimentos de recuo causados por cartas não concedem o salário.
* **Valorização por Visitas**: Cada vez que qualquer jogador para em um imóvel (com ou sem dono), ele é visitado. Isso aumenta o multiplicador de aluguel em `+0.1`, até o limite de `2.0` (dobro do aluguel base).
* **Imposto**: Cobra uma taxa de 5% sobre o patrimônio total (saldo + valor de compra de todas as propriedades possuídas).
* **Restituição**: Deposita 10% do valor do salário na conta do jogador.

---

## 🚀 Como Executar o Jogo

Certifique-se de ter o JDK (Java Development Kit) instalado em sua máquina.

1. **Abra o terminal** na pasta raiz do projeto:
   ```bash
   cd "C:\Users\marcu\.gemini\antigravity-ide\scratch\simulacao_tabuleiro"
   ```

2. **Compile o projeto**:
   ```bash
   javac -d bin src/Main.java src/model/*.java src/controller/*.java src/structure/*.java
   ```

3. **Execute o jogo**:
   ```bash
   java -cp bin Main
   ```

*(Nota: O jogo já inicia com 2 jogadores de teste pré-cadastrados e 12 imóveis inspirados em Cidades Brasileiras para permitir o início imediato ao escolher a opção **4. Iniciar Jogo**).*
