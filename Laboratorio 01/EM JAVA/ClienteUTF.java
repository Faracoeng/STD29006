//package engtelecom.std.tcp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Cliente que envia uma String em UTF por um socket TCP e espera
 * por uma resposta do servidor
 * 
 * 2014-08-24
 * @author Emerson Ribeiro de Mello
 */
public class ClienteUTF {
    
    public static void main(String[] args) throws IOException{
        int porta = 1234;
        String ip = "191.36.13.91";
        
        /* Estabele conexao com o servidor */
        Socket conexao = new Socket(ip,porta);
        System.out.println("Conectado! " + conexao);
        /*********************************************************/
        /* Estabelece fluxos de entrada e saida */
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
        DataInputStream fluxoEntrada = new DataInputStream(
                new BufferedInputStream(conexao.getInputStream()));
        /*********************************************************/
        /* Inicia comunicacao */
        fluxoSaida.writeUTF("Oi, eu sou o cliente!");
        fluxoSaida.flush();
        
        String mensagem = fluxoEntrada.readUTF();
        System.out.println("Servidor> " + mensagem);
        /*********************************************************/
        /* Fecha fluxos e socket */
        fluxoSaida.close();
        fluxoEntrada.close();
        conexao.close();
        
    }
    
}
