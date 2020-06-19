package std.client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        int porta = 1234;
        String ip = "127.0.0.1"; // loopback para testar na própria máquina

        /* Estabele conexao com o servidor */
        Socket conexao = new Socket(ip,porta);
        System.out.println("Conectado! " + conexao);
        /* Estabelece fluxos de entrada e saida */
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
        DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
        /* Inicia comunicacao */
        fluxoSaida.writeUTF("Oi, eu sou o cliente!");
        fluxoSaida.flush();
        String mensagem = fluxoEntrada.readUTF();
        System.out.println("Servidor > " + mensagem);
    }
}
