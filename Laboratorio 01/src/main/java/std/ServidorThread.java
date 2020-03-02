package std;

import java.net.Socket;

public class ServidorThread extends Thread {
    private Socket conexao;
    public ServidorThread(Socket c){
        this.conexao = c;
    }
    public void run(){
        // System.out.println("Thread executada");
        // protocolo :
        // servidor recebe mensagem do cliente
        // servidor responde cliente concatenando a mensagem recebi com uma string "s:"
        // somente finaliza a conexao se clinete mandar a string "fim"


    }
}
