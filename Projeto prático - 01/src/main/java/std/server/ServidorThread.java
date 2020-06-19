package std.server;


// Como servidor deve atender separadamente 4 Raspberry
// Decidi fazer um server MultThread

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServidorThread extends Thread{

    Socket conexao;

    public ServidorThread(Socket c) {
        this.conexao = c;
    }

    public void run() {
        System.out.println("Cliente conectou " + conexao);

        try {
            /* Estabelece fluxos de entrada e saida */
            DataInputStream fluxoEntrada = null;
            fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());

            //--------------------------------------------------------------------
            /* Inicia comunicacao */
            fluxoSaida.writeUTF("Matriz enviada");
            fluxoSaida.flush();
            String mensagem = fluxoEntrada.readUTF();
            System.out.println("Cliente -> " + mensagem);


            /* Fecha fluxos e socket */
            //saida.close();
           // entrada.close();
            conexao.close();

        } catch (IOException e){    //catch (IOException | IOException e) {
            e.printStackTrace();
        }
    }
}
