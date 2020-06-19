package std.server;

import java.net.Socket;

public class ServidorThread {
        private Socket conexao;

        public ServidorThread(Socket c){
            this.conexao = c;
        }
        public void run(){
            System.out.println("Thread executada");
        }
}
