package std.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static Gerenciador agenda = new Gerenciador();
    public static final String MENU = "\n         =========================================================" +
            "\n        |     1 - Criar nova lista                              |" +
            "\n        |     2 - adicionar valores em uma lista já existente   |" +
            "\n        |     3 - remover ultimo valor de uma lista             |" +
            "\n        |     4 - sair                                          |";

    public static void main(String[] args) throws IOException {
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
        System.out.println("Cliente > " + mensagem);
        fluxoSaida.writeUTF("Oi, eu sou o servidor!");





        /* Fecha fluxos e socket */
        fluxoEntrada.close();
        fluxoSaida.close();
        conexao.close();
        servidor.close();
    }
}
