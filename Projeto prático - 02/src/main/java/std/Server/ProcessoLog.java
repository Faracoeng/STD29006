package std.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ProcessoLog {

    // Atributos --------------------------------------------------------------------------------------------
    private String ip;
    private int porta;

    // Ao instanciar cada processo (trabalhador ouLOG) deve-se fornecer um arquivo texto contendo os
    // identificadores detodos os processos que participarão do sistema distribuído. O formato do arquivo
    // deverá conterum identificador de processo por linha
    private String arquivoProcessos;
    private int totalDeProcessos = 0;
    private ArrayList<String> listaDeWorkers = new ArrayList<>();


    public ProcessoLog(String ip, int porta, String arquivoProcessos) {
        this.ip = ip;
        this.porta = porta;

        this.arquivoProcessos = arquivoProcessos;
    }

    // Arquivo de processos (workers) que poderão enviar mensagens
    public void lerArquivoProcessos() {
        File arquivo = new File(this.arquivoProcessos);
        try {
            FileReader fin = new FileReader(arquivo);
            BufferedReader lerArquivo = new BufferedReader(fin);

            String linha = lerArquivo.readLine();
            while (linha != null) {
                // System.out.println("---> " + linha);
                listaDeWorkers.add(linha);
                linha = lerArquivo.readLine(); // lê da segunda até a última linha
            }
            this.totalDeProcessos = listaDeWorkers.size();
            //System.out.println(listaDeWorkers.size());
            lerArquivo.close(); //fechando fluxo de entrada
            fin.close(); //fechando arquivo
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }

    public void imprimirEventos() {
        // fazer uso dos relógio vetorial para imprimir em
        // seu console os eventos de acordo com a ordem que
        // foram gerados e não na ordem que chegaram ao processo LOG.
    }
// Main ------------------------------------------------------------------------------------------------

    public static void main(String[] args) throws IOException {
        // ThreadPoolExecutor p = new ThreadPoolExecutor(5, 10, 1, TimeUnit.HOURS, new ArrayBlockingQueue<Runnable>(10));
        //try{
        ProcessoLog processo = new ProcessoLog(args[0], Integer.parseInt(args[1]), args[2]);
        processo.lerArquivoProcessos();

        // Iniciar conexão TCP -------------------------------------------------------------------------------------

        // Variavel utilizada para verificar a quantidade de conexoes ativas no sistema
        int numeroDeConexoes = 0;
        // vetor de Threads responsável por guardar as conexoes realizadas com os clientes
        Thread procesos[] = new Thread[processo.totalDeProcessos];
        // registra servico na porta informada que devera sera a mesma fornecida por arquivo no processo worker e aguarda por conexoes
        ServerSocket servidor = new ServerSocket(processo.porta);

        // loop que deve ser executado (processo.totalDeProcessos) vezes, uma vez para cada conexao
        while (numeroDeConexoes < processo.totalDeProcessos) {

            System.out.println("Aguardando, restam " + String.valueOf(processo.totalDeProcessos - numeroDeConexoes) + " conexoes");
            // Aguarda cliente solicitar conexao TCP
            Socket conexao = servidor.accept();
            // Ao se conectar, conexao e salva no vetor de Threads
            procesos[numeroDeConexoes] = new ServidorThread(conexao,processo);
            System.out.println(numeroDeConexoes + 1 + "º WORKER conectado");
            // Registra numero de conexões
            numeroDeConexoes += 1;
        }


        // dispara todas as Threads após estabelecer conexao com todos os processos worker
        System.out.println("Timestamp       " + "     ORIGEM     " + "    destino     " + "    MENSAGEM     "+"    LogicalClock     ");

        for (Thread obj : procesos) {
            obj.start();
        }



        // ------------------------------------------------------------------------------------------------------------


        //Imprimir eventos no console
        processo.imprimirEventos();

    }
}
