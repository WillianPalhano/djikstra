package dijkstra;


/**
 * @author Willian José Palhano
 * @author Fernanda Buzzarello
 */
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlgoritmoDijkstra {

    final static int BRANCO = 0; //Vértice não visitado. Inicialmente todos os vértices são brancos
    final static int CINZA = 1; //Vértice visitado 
    static int G[][]; //Grafo 
    static int resultadoDijkstra[]; //definido no final da subrotina "algoritmoDijkstra"
    static int verticeInicial; //Definido no menu pelo usuário.
    static int tamanhoGrafo; // Definido quando o grafo for iniciado

    static int[] pi;  //Vetor do pai de um vértice
    static int[] d; //Vetor de distância
    static int[] cor; //Armazena os vértices visitados

    /**
     * Troca um número que representa a posição pela vértice do grafo.
     *
     * @param i Posição da letra
     * @return Uma String com a letra da posição i
     */
    public static String trocar(int i) {
        String letras = "ABCDEFGHIJ";
        if ((i >= 0) && (i <= letras.length())) {
            return letras.charAt(i) + "";
        } else {
            return "-";
        }
    }

    /**
     * Troca a letra pela posição na matriz de adjacência
     *
     * @param v Letra a ser troca pela posição
     * @return Um inteiro com a posição da letra no grafo
     */
    public static int destrocar(char v) {
        String letras = "ABCDEFGHIJ";
        int pos = -1;
        for (int i = 0; i < letras.length(); i++) {
            if (letras.charAt(i) == v) {
                pos = i;
            }
        }
        return pos;
    }

    /**
     * Exibe o caminho a ser percorrido no Grafo e o custo
     */
    public static String mostrarCaminho() {
        String caminho = "";
        for (int v = 1; v < tamanhoGrafo; v++) {
            caminho = caminho + trocar(pi[resultadoDijkstra[v]]) + " -> " + trocar(resultadoDijkstra[v]) + " custo: " + d[resultadoDijkstra[v]] + "\n";
        }
        return caminho;
    }

    /**
     * Executa o algoritmo de Dijkstra para Caminhos Mínimos de fonte única.
     *
     * Encontra a distância mais curta do vértice inicial para todos os outros vértices.
     *
     * Complexidade: O(V log V + E)
     *
     * @return TRUE se foi capaz de executar o algoritmo ou FALSE se o grafo não existe.
     */
    public static boolean algoritmoDijkstra() {
        if (G != null) {
            //Quantidade de vértices do grafo G
            System.out.println("TAMANHO DO G: " + tamanhoGrafo);

            //Instância o vetor de retorno
            int[] S = new int[tamanhoGrafo];

            //Converte a matriz em uma lista de arestas
            int n = G.length;
            List arestas = new LinkedList();
            for (int i = 0; i < tamanhoGrafo; i++) {
                for (int j = 0; j < tamanhoGrafo; j++) {
                    if (G[i][j] != 0 && i <= j) {
                        //Cria um vetor de 3 elementos para conter                     
                        //[0]=u(origem), [1]=v(destino), [2]=w(peso)
                        System.out.println("Adicionado na lista:  [0]=" + i + "(origem), [1]=" + j + "(destino), [2]=" + G[i][j] + "(peso)");
                        arestas.add(new int[]{i, j, G[i][j]});
                    }
                }
            }

            //Quantidade de arestas do grafo
            int E = arestas.size();

            //Realiza a inicialização das estimativas
            //Inicializa as cores com BRANCO, as distâncias com MAX_LENGH, e os pais dos vértices com -1
            d = new int[tamanhoGrafo];
            pi = new int[tamanhoGrafo];
            cor = new int[tamanhoGrafo];
            for (int v = 0; v < G.length; v++) {
                d[v] = Integer.MAX_VALUE;
                pi[v] = -1;
                cor[v] = BRANCO;
            }
            d[verticeInicial] = 0; //Define como 0 para poder iniciar a pesquisa no vértice A
            pi[verticeInicial] = 0; //Define como 0 para poder iniciar a pesquisa no vértice A
            String teste = "";
            //Percorre todos os vértice do grafo
            for (int i = 0; i < tamanhoGrafo; i++) {
                //extranctMin remove o vértice com a menor distância de Q
                int menorValor = Integer.MAX_VALUE;
                int indiceMenor = -1;
                for (int k = 0; k < tamanhoGrafo; k++) {
                    if (cor[i] == BRANCO && d[i] < menorValor) {
                        indiceMenor = i;
                        menorValor = d[i];
                    }
                }
                //Marca como visitado
                cor[indiceMenor] = CINZA;
                S[i] = indiceMenor;
                teste = teste + S[i] + ", ";
                //Percorre todas as arestas do grafo
                for (int j = 0; j < E; j++) {
                    int[] vertice = (int[]) arestas.get(j);
                    int u = vertice[0];//origem
                    int v = vertice[1];//destino
                    int w = vertice[2];//peso
                    //Faz o relaxamento para o vertice retirado de V
                    if (u == indiceMenor) {
                        if (d[v]/*Distancia de destino*/ > d[u] + w/*distância da origem + peso até destino*/) {/**/
                            d[v] = d[u] + w;
                            pi[v] = u;
                        }
                    }
                }
            }
            resultadoDijkstra = S;
            return true;
        } else {
            return false;
        }
    }

    public static void carregarGrafo() {
        int GrafoPre[][]
                = { //A  B  C  D  E  F  G  H    
                    {0, 5, 6, 0, 0, 0, 0, 0}, //A
                    {5, 0, 0, 2, 2, 0, 0, 0}, //B
                    {6, 0, 0, 2, 0, 4, 2, 0}, //C
                    {0, 2, 2, 0, 1, 0, 3, 0}, //D
                    {0, 2, 0, 1, 0, 0, 5, 1}, //E
                    {0, 0, 4, 0, 0, 0, 0, 3}, //F
                    {0, 0, 2, 3, 5, 0, 0, 2}, //G
                    {0, 0, 0, 0, 1, 3, 2, 0}};//H
        G = GrafoPre;
        tamanhoGrafo = G.length;
        System.out.println("GRAFO CARREGADO COM SUCESSO");
    }

    public static void main(String args[]) {

        int menu = 1;

        while (menu > 0 && menu < 11) {

            menu = Integer.parseInt(JOptionPane.showInputDialog("MENU\n\n"
                    + "1 - Carregar Grafo e exibi-lo\n"
                    + "2 - Executar Algoritmo Dijkstra\n"
                    + "3 - Mostrar caminhos do Grafo"));

            switch (menu) {
                case 1: {
                    carregarGrafo();
                    break;
                }
                case 2: {

                    String vertice = JOptionPane.showInputDialog("Insira o Vértice de início.");
                    vertice = vertice.toUpperCase();
                    char vert = vertice.charAt(0);
                    int inicio = destrocar(vert);
                    verticeInicial = inicio;
                    if(algoritmoDijkstra() == true){
                        JOptionPane.showMessageDialog(null, "Algoritmo realizado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "O grafo ainda não foi definido. Para carregar um grafo existente, selecione a opção 1 no menu.");
                    }
                    break;
                }
                case 3: {
                    JOptionPane.showMessageDialog(null, "Caminho:\n" + mostrarCaminho());
                    break;
                }
            }
        }
    }
}
