package std;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorThread extends Thread {
    private Socket conexao;
    private boolean conectado;
    public ServidorThread(Socket c){
        this.conexao = c; // Thread construida baseada na conexao com cliente
        this.conectado = true; // flag que verifica conexão com cliente

    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    // protocolo :
    // servidor recebe mensagem do cliente
    // servidor responde cliente concatenando a mensagem recebi com uma string "s:"
    // somente finaliza a conexao se clinete mandar a string "fim"
    public void run(){

        /* Estabelece fluxos de entrada e saida */
        DataInputStream fluxoEntrada = null;
        DataOutputStream fluxoSaida = null;
        while(conectado) {

            try {
                // ---------- fluxos de entrado e saida, comunicação entre server e cliente-------------
                fluxoEntrada = new DataInputStream(
                        new BufferedInputStream(conexao.getInputStream())
                );
                fluxoSaida = new DataOutputStream(conexao.getOutputStream());
                // -------------------------------------------------------------------------------
                /* inicia a comunicacao */
                String mensagem = fluxoEntrada.readUTF(); // mensagem do cliente lida
                if(mensagem.equals("fim" )|| mensagem.equals("FIM") || mensagem.equals("Fim")){
                    /* Fecha fluxos e socket */
                    fluxoEntrada.close();
                    fluxoSaida.close();
                    conexao.close();
                    setConectado(false);

                }
                System.out.println("Mensagem do cliente: "+ mensagem);
                fluxoSaida.writeUTF("s:" + mensagem); // servidor faz eco

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
