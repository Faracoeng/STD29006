package std.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorThread extends Thread {
    private Socket conexao;

    public ServidorThread(Socket c) {
        this.conexao = c;
    }

    public void run() {
        System.out.println("Thread executada");
        try {
            /* Registra servico na porta 1234 e aguarda por conexoes */
            ServerSocket servidor = new ServerSocket(1234);
            System.out.println("Aguardando por conexoes");
            Socket conexao = servidor.accept();
            System.out.println("Cliente conectou " + conexao);
            /* Estabelece fluxos de entrada e saida */
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
            /* inicia a comunicacao */
            String mensagem = fluxoEntrada.readUTF();
            System.out.println("Cliente> " + mensagem);
            fluxoSaida.writeUTF("Oi, eu sou o servidor!");


            /* Fecha fluxos e socket */
            fluxoEntrada.close();
            fluxoSaida.close();
            conexao.close();
            servidor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
