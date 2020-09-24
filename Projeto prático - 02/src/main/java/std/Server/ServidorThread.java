package std.Server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorThread extends Thread {

    Socket conexao;
    public boolean eventosLidos = false;
    public boolean ativar = true;
    ProcessoLog proc;

    public boolean isEventosLidos() {
        return eventosLidos;
    }
    public ServidorThread(Socket c, ProcessoLog p) {
        this.conexao = c;
        this.proc = p;
    }

    public void run() {
        // System.out.println("Cliente conectou " + conexao);

        /* Estabelece fluxos de entrada e saida */
        DataInputStream fluxoEntrada = null;
        try {
            fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());

            //--------------------------------------------------------------------
            /* Inicia comunicacao */
            fluxoSaida.writeUTF("ativar");
            fluxoSaida.flush();
            String mensagem = fluxoEntrada.readUTF();
            String idWorker = mensagem;
            //System.out.println("Cliente -> " + mensagem);

            //O campo (CurrentLogicalClock) deve exibiro  relógio  vetorial  atual
            // do  processo  após  o  processamento  ou  recebimento  do  evento.
            // Já  o campo MessageLogicalClock deve imprimir o relógio vetorial que
            // estava contido na mensagem recebida. Se for um evento local,
            // então esse campo deverá exibir somente três hífens:---.

            int contador = 1;
            while (this.ativar){
                mensagem = fluxoEntrada.readUTF();
                // se for avida que a leitura do arquivo acabou, regista informaçao
                if(mensagem.equals("terminei")){
                    this.eventosLidos = true;
                    System.out.println(mensagem);
                }
                else System.out.println(mensagem);
                //System.out.println(mensagem + "------>" + idWorker + " " + contador + " mensagem");
                // contador += 1;
            }
            System.out.println("Fim da conexão com " + idWorker);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
