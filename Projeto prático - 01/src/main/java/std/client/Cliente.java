package std.client;

// A classe cliente representa uma RaspBerry, que ouvirá o broadcast contendo o
// IP do servidor e estabelecerá uma conecção TCP com ele


import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        try {

            //Aberto um DatagramSocket no cliente na porta 1234 para receber o broadcast
            DatagramSocket serverSocket = new DatagramSocket(1234);
            // Vetor de byte para receber dados do pacote de broadcast
            byte[] receiveData = new byte[2048];
            // -----------------------------------------------------------------------

            // Cria-se um DatagramPacket para receber pacote proveniente do broadcast que o servidor realizará
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            // Socket deverá receber o pacote
            serverSocket.receive(receivePacket);
            // fecha o seocket após receber o IP do servidor para não dar conflito com outros clientes
            serverSocket.close();
            // pegando dado do pacote
            String dado = new String(receivePacket.getData());

            //----------------------------respondendo---------------------------------------------------------------

            /* Estabelendo conexao com o servidor */
            Socket conexao = new Socket(dado, 4321);
            System.out.println("Conectado! " + conexao);

            /* Estabelece fluxos de entrada e saida */
            DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
            DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
            //------------------------------------------------------------------------------------------------------

            /* inicia a comunicacao */

            String mensagem = fluxoEntrada.readUTF();
            System.out.println("Servidor -> " + mensagem);
            fluxoSaida.writeUTF("Matriz recebida");

            /* Fecha fluxos e socket */
            fluxoEntrada.close();
            fluxoSaida.close();
            conexao.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
