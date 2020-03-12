package Cliente;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        int porta = 1234; // porta de conexão do servidor
        String ip = "127.0.0.1"; //IP do servidor
        String mensagemCliente = "";

        /* Estabelece conexao com o servidor */
        Socket conexao = new Socket(ip,porta);

        while(!mensagemCliente.equals("fim")){ // outra forma de encerrar o loop
            /* Estabelece fluxos de entrada e saida */

            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
            DataInputStream fluxoEntrada = new DataInputStream(
                    new BufferedInputStream(conexao.getInputStream()));

            /* Inicia comunicacao */
            Scanner teclado = new Scanner(System.in);
            System.out.println("Digite uma mensagem para o servidor:");
            mensagemCliente = teclado.nextLine();
            fluxoSaida.writeUTF(mensagemCliente);
            // Método flush() limpa os buffers desse fluxo e faz com que todos os
            // dados armazenados em buffer sejam gravados no arquivo.
            fluxoSaida.flush();
            String mensagem = fluxoEntrada.readUTF();
            System.out.println("Servidor disse: " + mensagem);
            if(mensagemCliente.equals("fim")){
                /* Fecha fluxos e socket */
                fluxoSaida.close();
                fluxoEntrada.close();
                conexao.close();
            }
        }
    }
}
