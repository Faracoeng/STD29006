package Cliente;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws IOException {
        int porta = 1234;
        String ip = "127.0.0.1";
        Scanner ler = new Scanner(System.in);

        /* Estabele conexao com o servidor */
        Socket conexao = new Socket(ip, porta);
        System.out.println("Conectado! " + conexao);
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
        DataInputStream fluxoEntrada = new DataInputStream(
                new BufferedInputStream(conexao.getInputStream()));

        String op = "";
        while (!op.equals("5")) {
            System.out.println(fluxoEntrada.readUTF());
            op = ler.next();
            fluxoSaida.writeUTF(op);
            fluxoSaida.flush();
            /* Fecha fluxos e socket */
            fluxoSaida.close();
            fluxoEntrada.close();
            conexao.close();
        }
    }
}