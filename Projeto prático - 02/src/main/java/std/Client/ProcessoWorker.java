package std.Client;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

    // Sorteia um valor de Jitter baseado na semente passada por args.
    private int sortearJitter() {
        Random r = new Random(this.sementeUnica);
        return r.nextInt(tempoJitter);
    }

    // Sorteia um numero aleatório baseado no numero de processos que existem no arquivo de processos do tipo 'S'

    private String sortearProcesso() {
//        System.out.println("varrendo lista:");
//        for (String obj : listaDeProcessos) {
//            System.out.println(obj);
//        }
        return listaDeProcessos.get(new Random(this.sementeUnica).nextInt(listaDeProcessos.size()));
    }

    // Sorteia tempo máximo que o processo ficará aguardandoantes de processar o próximo evento
    private int sortearTempoMaximo() {
        Random r = new Random(this.sementeUnica);
        return r.nextInt(tempoMaximo);
    }

    private String horarioAtual() {
        java.util.Date atual = new java.util.Date();
        java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat("hh:mm:ss,SSS");
        return formater.format(atual);
    }

    private void lerEventos() {
        File arquivo = new File(this.arquivoEventos);
        try {
            FileReader fin = new FileReader(arquivo);
            BufferedReader lerArquivo = new BufferedReader(fin);

            String linha = lerArquivo.readLine();
            while (linha != null) {
                //System.out.println("---> " + linha);

                listaDeEventos.add(linha.split(","));
                linha = lerArquivo.readLine(); // lê da segunda até a última linha

            }
            lerArquivo.close(); //fechando fluxo de entrada
            fin.close(); //fechando arquivo
        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }

    // Le arquivo de processos passado como args e contabiliza total de processos envolvidos no sistema
    private void lerProcessos() {
        File arquivo = new File(this.arquivoProcessos);
        try {
            FileReader fin = new FileReader(arquivo);
            BufferedReader lerArquivo = new BufferedReader(fin);

            String linha = lerArquivo.readLine();
            String[] dadosWorker;
            while (linha != null) {
                // System.out.println("---> " + linha);

                dadosWorker = linha.split("-");
                // Se nao for ele mesmo
                if (!dadosWorker[2].equals(this.idUnico)) {
                    if (dadosWorker[2].equals("log") || dadosWorker[2].equals("LOG") || dadosWorker[2].equals("Log")) {
                        this.ipLog = dadosWorker[0];
                        this.portaLog = dadosWorker[1];
                    } else {
                        tabelaProcessos.put(dadosWorker[2], new String[]{dadosWorker[0], dadosWorker[1]});
                        listaDeProcessos.add(dadosWorker[2]);
                        this.numeroDeProcessos += 1;
                        //System.out.println("-->" + this.numeroDeProcessos);
                    }
                }
                linha = lerArquivo.readLine(); // lê da segunda até a última linha
            }
            this.relogioVetorial = new int[this.numeroDeProcessos];


            lerArquivo.close(); //fechando fluxo de entrada
            fin.close(); //fechando arquivo

        } catch (Exception e) {
            System.err.println("erro: " + e.toString());
        }
    }

    // Metodo sincronizado para receber Strings das threads disparadas em executarEvento()
    public synchronized void adicionarEventoNaFila(String evento) {
        // Se o metodo nao estver ocupado aguarda
        while (this.metodoOcupado) {
            try {
                System.err.println("Aguardando para adicionarEventoNaFila()");
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
        // Saiu do while porque alguma Thread qer adicionar evento na fila
        //adiciona evento na fila
        this.filaEventos.add(evento);
        //System.out.println("--> adicionado " + evento + " na fila");
        // se receber mensagem de outro processo estará ocupado, senao estara livre
        if (!chegouMensagem) {
            this.metodoOcupado = false;
        } else {
            this.metodoOcupado = true;
        }
        //System.out.println("linha lida");
        //notify();
    }

    public synchronized void receberUDPColocarNaFila(String evento) {
        this.chegouMensagem = true;
        this.metodoOcupado = true;
        //enquanto a cadeira estiver cadeiraOcupada, aguarde sua vez
        while (!this.metodoOcupado) {
            try {
                // espere até a Thread atual sair do metodo
                System.err.println("Aguardando para adicionar mensagem recebida na fila");
                wait();
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }
        // Metodo livre! Fazer com que o mensagem seja adicionada na fila
        // System.out.println("----------------> adicionouuuuuuuuu mensagem de outro processsooooo");
        this.filaEventos.add(evento);

        // avisar a thread que tem gente no metodo
        this.chegouMensagem = false;
        this.metodoOcupado = false;
        //System.out.println("Mnesagem recebida");
        notify();
    }

    // identifica os tipos de eventos e ja encaminha para LOG e executa localmente ou encaminha para outro processo
    public void executarEventos(DataInputStream entrada, DataOutputStream saida, int tempoMax) throws IOException, InterruptedException {

        // Dispara Threads
        receptorPacotes.start();
        leitorEventos.start();
        String conteudoEvento;
        // Verificar forma de manter loop só enquanto sistema estiver rodando,
        // vai vir do LOG provavelmente
        System.out.println("Timestamp       " + "    ORIGEM     " + "   destino       " + "MENSAGEM     " + "   CurrentLogicalClock      " + "    MessageLogicalClock");
        while (true) {
            // ficará aguardando antes de processar o próximo evento
            TimeUnit.MILLISECONDS.sleep(tempoMax);
            // se a fila de eventos nao estiver vazia, deverá pegar o conteudo mais antigo dela dela
            if (!filaEventos.isEmpty()) {
                //System.out.println("Entrou, logo fila nao esta vazia e seu tamanho é: " + filaEventos.size());
                conteudoEvento = this.filaEventos.remove();
                // System.out.println("primeiro conteudo da fila é: " + conteudoEvento.charAt(0));

                // ----------------------------------------------------------------------------------------------------
                // se for um evento do tipo 'S'
                if (conteudoEvento.charAt(0) == 'S' || conteudoEvento.charAt(0) == 's') {
                    // gerando processo destino aleatorio no caso de ser um evento do tipo 'S'
                    String processoSorteado = sortearProcesso();
                    String[] mensagemEvento = conteudoEvento.split("_");
//                    System.out.println("enviado: " + tabelaProcessos.get(processoSorteado)[0] + tabelaProcessos.get(processoSorteado)[1] +
//                            "-" + mensagemEvento[1] + Arrays.toString(this.relogioVetorial));
                    enviarMensagem(tabelaProcessos.get(processoSorteado)[0], tabelaProcessos.get(processoSorteado)[1],
                            this.idUnico + "-" + mensagemEvento[1] + "-" + Arrays.toString(this.relogioVetorial));
                    // this.indiceProcesso - 1 porque leitura de indice de vetor comeca da posicao 0
                    // Incrementa com evento do tipo 'S' ??
                    this.relogioVetorial[this.indiceProcesso - 1]++;
                    System.err.println(horarioAtual() + "          " + this.idUnico + "              " + processoSorteado + "          " + mensagemEvento[1] + "          " + Arrays.toString(this.relogioVetorial));
                    TimeUnit.MILLISECONDS.sleep(this.tempoJitter); // pode dar erro aqui
                    saida.writeUTF(horarioAtual() + "          " + this.idUnico + "              " + processoSorteado + "          " + mensagemEvento[1] + "          " + Arrays.toString(this.relogioVetorial));
                    saida.flush();
                }
                // ----------------------------------------------------------------------------------------------------
                // se for um evento do tipo 'L'
                else if (conteudoEvento.charAt(0) == 'L' || conteudoEvento.charAt(0) == 'l') {
                    String[] mensagemEvento = conteudoEvento.split("_");
                    // incrementa seu relogio logico no relogio vetorial para identificar execução do evento
                    // this.indiceProcesso - 1 porque leitura de indice de vetor comeca da posicao 0
                    this.relogioVetorial[this.indiceProcesso - 1]++;
                    // imprime evento no console
                    System.err.println(horarioAtual() + "        LOCAL         " + "   LOCAL        " + mensagemEvento[1] + "          " + Arrays.toString(this.relogioVetorial) + "           ---");
                    //Manda pro LOG
                    // Se for um evento local, então esse campo deverá exibir somente três hífens: ---.
                    TimeUnit.MILLISECONDS.sleep(this.tempoJitter);
                    saida.writeUTF(horarioAtual() + "        LOCAL         " + "   LOCAL        " + mensagemEvento[1] + "          " + Arrays.toString(this.relogioVetorial));
                    saida.flush();
                }
                // ----------------------------------------------------------------------------------------------------
                // se for um evento recebido por outro processo
                else {
                    // separa mensagem recebida em conteudo e relogio logico
                    String[] mensagemEvento = conteudoEvento.split("-");
                    // System.out.println("mensagem recebida foi -------------------------------> " + mensagemEvento[1] + " relogio Logico é: " + mensagemEvento[2]);
                    String relogioRecebido = mensagemEvento[2];
                    mensagemEvento[2] = mensagemEvento[2].replace("[", "");
                    mensagemEvento[2] = mensagemEvento[2].replace("]", "");
                    mensagemEvento[2] = mensagemEvento[2].replace(" ", "");

                    String[] componentesRelogio = mensagemEvento[2].split(",");
                    //relogio que sera recebido pela mensagem
                    // System.out.println("Relogio recebido na mensagem:");
                    int[] relogioRecebidoMensagem = new int[this.numeroDeProcessos];
                    for (int i = 0; i < this.numeroDeProcessos; i++) {
                        //System.out.println(i + " -> " + componentesRelogio[i]);0
                        relogioRecebidoMensagem[i] = Integer.parseInt(componentesRelogio[i].trim());

                    }
                    System.out.println("Relogio logico recebido: " + relogioRecebido);
                    System.out.println("Meu relogio antes do ajuste: " + Arrays.toString(this.relogioVetorial));
                    ajustaRelogio(relogioRecebidoMensagem);
                    System.out.println("relogio logico ajustado: " + Arrays.toString(this.relogioVetorial));
                    System.err.println(horarioAtual() + "          " + mensagemEvento[0] + "              " + this.idUnico + "           " + mensagemEvento[1] + "          " + Arrays.toString(this.relogioVetorial) + "          " + relogioRecebido);
                    TimeUnit.MILLISECONDS.sleep(this.tempoJitter);
                    saida.writeUTF(horarioAtual() + "          " + mensagemEvento[0] + "              " + this.idUnico + "           " + mensagemEvento[1] + "          " + relogioRecebido);
                    saida.flush();

                }
//                // verifica se leitura do arquivo de eventos foi finalizada e informa o LOG
//                if(leitorEventos.terminei) {
//                    saida.writeUTF("terminei");
//                    saida.flush();
//                }
            }
        }

    }

    // Enviar mensagem no caso do evento ser do tipo "S"
    private void enviarMensagem(String ip, String portaDestino, String mensagem) throws IOException {

        // vetores para mandar mensagem----------------
        byte[] dadosRecebidos = new byte[2048];
        byte[] dadosEnviados;
        // --------------------------------------------
        // pegando ip do processo destino
        InetAddress ipDestino = InetAddress.getByName(ip);
        // tranformando mensagem em bytes
        dadosEnviados = mensagem.getBytes();
        // criando datagrama
        DatagramPacket pacoteEnviado = new DatagramPacket(dadosEnviados, dadosEnviados.length, ipDestino, Integer.parseInt(portaDestino));
        // enviando
        cliente.send(pacoteEnviado);
    }

    // Metodo responsável por ajustar relogio vetorial toda vez que receber mensagem de outro processo
    private void ajustaRelogio(int[] relogioRecebido) {
        for (int i = 0; i < this.relogioVetorial.length; i++) {
            // Verifica se há alguma posiciao do relogio vetorial local com o valor abaixo
            // do relogio vetorial recebido com a mensagen
            if (this.relogioVetorial[i] < relogioRecebido[i]) {
                // Caso tenha, altera o valor
                this.relogioVetorial[i] = relogioRecebido[i];
            }
        }
        // registra o evendo em sua posicao no relogio vetorial local
        this.relogioVetorial[this.indiceProcesso - 1]++;
    }

    //----------------------MAIN---------------------------------------------------------------------------------------
    public static void main(String[] args) {
        try {
            // ------construindo worker-----------------------------------------------------------------------------------------------------
//            ProcessoWorker processo = new ProcessoWorker(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]),
//                    Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6], args[7], args[8]);

            // ------------------------------ Construtor caso dados venham do arquivo de processos
            ProcessoWorker processo = new ProcessoWorker(args[0], Integer.parseInt(args[1]), args[2], Integer.parseInt(args[3]),
                    Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6], args[7]);
            // ------------------------------
            // inicia leitura dos procesos no aruivo texto
            processo.lerProcessos();


            // -----------------------relogio vetorial-----------------------------------------------------------------------------

            processo.lerEventos();
            //System.out.println(processo.horarioAtual());

            //----------------------------conectando com LOG---------------------------------------------------------

            /* Estabelendo conexao TCP com o LOG porta definida */
            Socket conexao = new Socket(processo.ipLog, Integer.parseInt(processo.portaLog));
            // System.out.println("Conectado! " + conexao);

            /* Estabelece fluxos de entrada e saida */
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());

            //iniciando comunicação-------------------------------------------------
            String mensagem = fluxoEntrada.readUTF();
            // Aguarda Log liberar propcesso para execução
            while (!processo.estouPronto) {
                if (mensagem.equals("ativar")) processo.estouPronto = true;
            }
            //------------------------------------------------------------------------------------------------------
            // envia seu id para Log identificar com qual woker esta conectado
            fluxoSaida.writeUTF(processo.idUnico);
            fluxoSaida.flush();
            // ate aqui ok


            // um valor em milissegundos para ser usado como o tempo máximo que o processo ficará aguardando antes de processar o próximo evento
            int tempoMax = processo.sortearTempoMaximo();
            // Thread que le eventos da lista de ventos
            processo.leitorEventos = new LeitorDeArquivoEventos(tempoMax, processo.listaDeEventos, processo, processo.idUnico,fluxoSaida);
            // começar a ouvir pacotes UDP
            processo.receptorPacotes = new ReceberPacoteUDP(processo.porta, processo);

            processo.executarEventos(fluxoEntrada, fluxoSaida, tempoMax);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //--------------------------------------------------------------------------------------------------------------
    private void imrimirTabelaProcessos() {
        //imprimir valores da tabela de workers
        for (String key : tabelaProcessos.keySet()) {
            //Capturamos o valor a partir da chave
            String[] valor = tabelaProcessos.get(key);
            System.out.println(key + " = " + "ip:" + valor[0] + " porta:" + valor[1]);
        }
    }

}
