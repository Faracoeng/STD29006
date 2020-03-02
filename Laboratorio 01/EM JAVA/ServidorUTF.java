package engtelecom.std.tcp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor que espera por uma mensagem do cliente (String em UTF) e depois
 * envia uma String de resposta, tambem em UTF
 * 
 * 2014-08-24
 * @author Emerson Ribeiro de Mello
 */
public class ServidorUTF {

    public static void main(String[] args) throws IOException {      
        
        /* Registra servico na porta 1234 e aguarda por conexoes */
        ServerSocket servidor = new ServerSocket(1234);
        
        System.out.println("Aguardando por conexoes");
        Socket conexao = servidor.accept();
        System.out.println("Cliente conectou " + conexao);
        /*********************************************************/
        /* Estabelece fluxos de entrada e saida */
        DataInputStream fluxoEntrada = new DataInputStream(
                new BufferedInputStream(conexao.getInputStream()));
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
        /*********************************************************/
        /* inicia a comunicacao */
        String mensagem = fluxoEntrada.readUTF();
        System.out.println("Cliente> " + mensagem);
        
        fluxoSaida.writeUTF("Oi, eu sou o servidor!");
        /*********************************************************/
        
        /* Fecha fluxos e socket */
        fluxoEntrada.close();
        fluxoSaida.close();
        conexao.close();
        servidor.close();
    }
    
}
