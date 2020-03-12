package std;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String args[]) throws IOException {
        // socket aberto na porta 1234
        ServerSocket servidor = new ServerSocket(1234);
        while(true){
            // servidor aguarda por conexoes de clientes
            Socket conexao = servidor.accept();
            // Quando se conectar, cria uma Thread para essa conexao
            Thread t = new ServidorThread(conexao);
            // e dispara ela
            t.start();}
    }
}
