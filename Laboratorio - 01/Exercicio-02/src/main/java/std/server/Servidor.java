package std.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String args[]) throws IOException {
        ServerSocket servidor = new ServerSocket(1234);


        while(true){
            // Servidor sempre aguarda uma conecção
            Socket conexao = servidor.accept();
            // ao aceitar, dispara uma Thread para tratar
            // separadamente cada conexao com cada cliente
            Thread t = new ServidorThread(conexao);
            t.start();
        }
    }
}
