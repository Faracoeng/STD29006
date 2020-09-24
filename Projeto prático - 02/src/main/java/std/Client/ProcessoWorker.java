package std.Client;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessoWorker {

    private String ip;
    private int porta;
    private int portaEnvioUDP;
    private boolean estouPronto = false;
    private DatagramSocket cliente;
    private ReceptorPacoteUDP receptorPacotes;
    private LeitorDeArquivosEventos leitorEventos;
    private Queue<String> filaEventos;
    // Na primeira vez, fila estará vazia
    private boolean metodoOcupado = false;
    private boolean chegouMensagem = false;

    // recebendo ip e porta por arquivos--------------------------------------------------------------------------------

    // parametros p1
    // 127.0.0.1 1245 p1 12345 1000 3 arquivoProcessos.txt eventosp1.txt
    //parametros p2
    // 127.0.0.1 1478 p2 14785 2000 4 arquivoProcessos.txt eventosp2.txt
    //parametros p3
    // 127.0.0.1 1596 p3 14875 1500 5 arquivoProcessos.txt eventosp3.txt
    // -----------------------------------------------------------------------------------------------------------------


    // Atributos Sistema-------------------------------------------------------------------------------------------
    private String idUnico = "";
    private int sementeUnica;
    // valor em milissegundos para ser usado como o tempo máximo que o processo ficará aguardando antes de processar o próximo evento
    private int tempoMaximo;
    // um valor em milissegundos para variação de atraso máximo entre o envio de uma mensagem para um processo
    // worker e o envio de umamensagem para o processo Log
    private int tempoJitter;
    private String arquivoProcessos = "";
    // Numero de processos ja inicia obrigatoriamnete com 1, pois este ao ser instanciado sera 1 processo
    private int numeroDeProcessos = 1;
    String arquivoEventos = "";
    private ArrayList<String[]> listaDeEventos = new ArrayList<>();
    private ArrayList<String> listaDeProcessos = new ArrayList<>();

    // -----verificar--------
    // informações para localização dos demais processos (Log e demais workers) -----------------------------------
    private String ipLog;
    private String portaLog;

    private String origem;
    private String destino;
    private String mensagem;
    private HashMap<String, String[]> tabelaProcessos;
    private int relogioVetorial[];
    private int indiceProcesso;
    // ----------------------
    public ProcessoWorker(String ip, int porta, String idUnico, int sementeUnica, int tempoMaximo,
                          int tempoJitter, String arquivoProcessos, String arquivoEventos) throws SocketException {
        this.ip = ip;
        this.porta = porta;
        // recebe a porta passada como linha de comando, e incrementa em 1 para utilizar como porta para socket UDP
        this.portaEnvioUDP = this.porta + 1;
        this.idUnico = idUnico;
        this.sementeUnica = sementeUnica;
        this.tempoMaximo = tempoMaximo;
        this.tempoJitter = tempoJitter;
        this.arquivoProcessos = arquivoProcessos;
        this.arquivoEventos = arquivoEventos;
        // SOCKET UDP na porta que será utilizada para enviar mensagens para outros workers
        //this.ReceberPacoteUDP receptorPacotes = new ReceberPacoteUDP(this.porta);
        this.cliente = new DatagramSocket(this.portaEnvioUDP);
        // fila de eventos para executar
        this.filaEventos = new LinkedList<>();
        // Define o indice de cada worker no  relogio vetorial através do pID deste
        // garante pegar qualquer indice de processo
        this.indiceProcesso = Integer.parseInt(idUnico.substring(idUnico.length() - (idUnico.length() - 1)));
        //this.ipLog = ipLog;
        this.tabelaProcessos = new HashMap<>();

    }

}
