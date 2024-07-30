import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoDaForca {
	
	private ArrayList<String> palavrasList = new ArrayList<String>();
	private ArrayList<String> dicasList = new ArrayList<String>();
	private String[] palavraSorteada;
	private ArrayList<String> palavraAdivinhada = new ArrayList<String>();
	private String dica;
	private int acertos = 0;
	private int numeroPenalidade = 0;
	private String[] nomePenalidade = {"Sem penalidades!", "Cabeça", "Tronco", "Braço Direito", "Braço Esquerdo", "Perna Direita", "Perna Esquerda"};
	private boolean terminou = false;
	private ArrayList<Integer> ocorrencias;
	private ArrayList<String> historicoLetras = new ArrayList<>();
	
	
		/* Método: JogoDaForca();
		 * Descrição: coleta os dados de um arquivo separado e no final os adiciona em listas;*/
		public JogoDaForca() throws Exception {
			InputStream stream = this.getClass().getResourceAsStream("/dados/palavras.txt");
			if (stream == null)
				throw new Exception("Arquivo de palavras inexistente");
			Scanner arquivo = new Scanner(stream);
		
			String linha;
			while (arquivo.hasNext()) {
				linha = arquivo.nextLine().toUpperCase();
			
				this.palavrasList.add(linha.split(";")[0]);
				this.dicasList.add(linha.split(";")[1]);
			}
			arquivo.close();
		
		
		
		}
	
		/* Método: iniciar;
		 * Descrição: inicia o jogo escolhendo uma palavra aleatória
		 * e a dica da palavra escolhida, e também adiciona "*" no lugar
		 * da palavra que terá que ser adivinhada, que é armazenada em uma lista*/
		public void iniciar() {
			Random random = new Random();
			int indice = random.nextInt(palavrasList.size());
			this.palavraSorteada = palavrasList.get(indice).split("");
			this.dica = dicasList.get(indice);
			
			for (int i = 0; i < palavraSorteada.length; i++) {
				this.palavraAdivinhada.add("*");
			}
		}
	
		public String getPalavra() {
			String s = "";
			for (int i = 0; i < palavraSorteada.length; i++) {
				s += palavraSorteada[i];
			}
			return s;
			
		}
		
		public String getDica() {
			return this.dica;
		}
		
		public int getTamanho() {
			return this.palavraSorteada.length;
		
		}
		
		/* Método: getOcorrencias;
		 * Descriçao: pega o caractere informado pelo usuário e verfica se:
		 * se foi digitado algo, se foi digitado apenas 1 caractere,
		 * se não é letra com caractere especial e se foi uma letra já informada anteriormente.
		 * Não havendo nenhum erro, a letra é guardada em umma lista para servir de verificação
		 * para tentativas futura e estando tudo ok na rodada, adicoina a letra no lugar do "*"
		 * que aparece para o usuário, mostrando aonde estão essas letras, contabiliza acerto/erro,
		 * guarda os índices das letras já acertadas e verifica se o jogo já terminou ou não.*/
		public ArrayList<Integer> getOcorrencias (String letra) throws Exception {
			String letraMaiuscula = letra.toUpperCase();
			ocorrencias = new ArrayList<>();
			if (letraMaiuscula == "") 
				throw new Exception("Digite uma letra!");
			
			if (letraMaiuscula.length() > 1)
				throw new Exception("Digite apenas 1 caractere!");
			
			
			if (!letraMaiuscula.matches("[A-Z]"))
				throw new Exception("Digite apenas letras!");
			
			if (historicoLetras.contains(letraMaiuscula))
				throw new Exception("Você já tentou essa letra anteriormente!");
			
			this.historicoLetras.add(letraMaiuscula);
			
			for (int i = 0; i < palavraSorteada.length; i++) {
				if (palavraSorteada[i].equals(letraMaiuscula)) {
					this.acertos += 1;
					this.palavraAdivinhada.set(i, letraMaiuscula);
					System.out.println(historicoLetras);
					this.ocorrencias.add(i);
				}
				
				
			}
			
			if (this.ocorrencias.size() == 0) {
				this.numeroPenalidade ++;
				
			}
			if (!this.palavraAdivinhada.contains("*") || this.numeroPenalidade == 6)
				this.terminou = true;
			return this.ocorrencias;
			
		}
		
		
		public boolean terminou() {
			return this.terminou;
		}
		
		/* Método: getPalavraAdivinhada;
		 * Descrição: retorna em formato de string como está a
		 * palavra que o usuário está tentando adivinhar*/
		public String getPalavraAdivinhada() {
			String palavraAdivinhada2 = "";
			for (int i = 0; i < this.palavraAdivinhada.size(); i++) {
				palavraAdivinhada2 += this.palavraAdivinhada.get(i);
			}
			return palavraAdivinhada2;
			
		}
		
		public int getAcertos() {
			return this.acertos;
		}
		
		public int getNumeroPenalidade() {
			return this.numeroPenalidade;
		}
		
		public String getNomePenalidade() {
			return this.nomePenalidade[this.numeroPenalidade];
			
		}
		
		public String getResultado() {
			if (this.acertos == palavraSorteada.length) return "Você venceu!";
			else if (this.numeroPenalidade == 6) return "Você foi enforcado!";
			else return "Jogo em andamento!";
		}
 		
		
		
		
		
	

}
