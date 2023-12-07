package maquinaTuring;
import java.util.ArrayList;
import java.util.Scanner;

public class MaquinadeTuring {

	private static String estadoAtual;
	private static int cabeca = 0;
	private static ArrayList<String> fita = new ArrayList<>(200);
	private static final String[][] transicoes = {
			{"q0", "1", "q1", "R", "X"},
			{"q0", "B", "q2", "R", "B"},
			{"q1", "1", "q0", "R", "X"},
			{"q1", "B", "q1", "L", "B"},
			{"q2", "", "q2", "L", ""}
	};

	public static void main(String[] args) {
		// Inicializando a fita com símbolos 'B'
		for (int i = 0; i < 100; i++) {
			fita.add("B");
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite uma entrada");
		String entrada = scanner.nextLine();

		try {
			escreverNaFita(entrada);
			for (int i = 0; i < fita.size(); i++) {
				if (lerSimbolo(i).equals("1")) {
					verificaEntradaUnariaPar();
				}
			}
			System.out.println("Fita final: " + fita);
		} catch (Exception e) {
		}
	}

	private static void escreverNaFita(String entrada) throws Exception {
		int posicao = ((fita.size() / 2) - (entrada.length() / 2));
		for (int i = 0; i < entrada.length(); i++) {
			escreverSimbolo(posicao, entrada.charAt(i));
			for (int j = 0; j < i; j++) {
				fita.add(fita.remove(fita.size() - 1));
			}
			if (entrada.charAt(i) != '1') {
				throw new Exception("A linguagem deverá conter apenas símbolos igual a '1'");
			}
			posicao++;
		}
	}

	public static void verificaEntradaUnariaPar() throws Exception {
		// Encontrar o primeiro símbolo da entrada
		while (cabeca < fita.size() && lerSimbolo(cabeca).equals("B")) {
			moverCabecaParaDireita();
		}

		estadoAtual = transicoes[0][0];
		// Começar o processamento apenas se encontrou um símbolo da entrada
		if (cabeca < fita.size()) {
			while (cabeca >= 0 && cabeca < fita.size()) {
				String simboloAtual = lerSimbolo(cabeca);
				String simboloLido = "";

				for (String[] transicao : transicoes) {
					if (transicao[0].equals(estadoAtual) && transicao[1].equals(simboloAtual)) {
						// Realizar ação da transição
						simboloLido = transicao[1];
						escreverSimbolo(cabeca, transicao[4].charAt(0));

						if (transicao[3].equals("R")) {
							moverCabecaParaDireita();
						} else if (transicao[3].equals("L")) {
							moverCabecaParaEsquerda();
						}

						// Atualizar o estado
						estadoAtual = transicao[2];

						break;
					}
				}

				if (estadoAtual.equals("q2")) {
					// Verifica se está no estado q2 apenas após ler a entrada
					aceitarEntrada();
					break;
				}

				if (estadoAtual.equals("q1") && simboloLido.equals("B")) {
					// Se estiver no estado q1 e ler um espaço em branco, rejeitar
					System.err.print("Saída: " + false + ". A quantidade de 'X' na fita não é par.");
					throw new Exception();
				} else if (estadoAtual.equals("q0") && simboloLido.equals("B")) {
					// Se voltar para o estado q0 antes de ler um espaço em branco, continuar o processamento
					break;
				}
			}
		} else {
			// Se não encontrou um símbolo da entrada
			System.err.print("A entrada está vazia ou contém apenas espaços em branco.");
			throw new Exception();
		}
	}


	private static void aceitarEntrada() {
		System.out.println("Saída: " + true + ". Entrada aceita! Estado final: " + estadoAtual);
		// Adicione lógica adicional conforme necessário
	}



	private static String lerSimbolo(int indice) {
		return fita.get(indice);
	}

	private static void escreverSimbolo(int posicao, char simbolo) {
		fita.set(posicao, String.valueOf(simbolo));
	}

	private static void moverCabecaParaEsquerda() {
		cabeca--;
	}

	private static void moverCabecaParaDireita() {
		cabeca++;
	}
}