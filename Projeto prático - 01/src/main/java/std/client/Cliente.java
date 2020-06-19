package std.client;

// A classe cliente representa uma RaspBerry, que ouvirá o broadcast contendo o
// IP do servidor e estabelecerá uma conecção TCP com ele


import java.net.DatagramSocket;

public class Cliente {
    public static void main(String[] args) {
        try {

            //Aberto um DatagramSocket no cliente na porta 1234 para receber o broadcast
            DatagramSocket serverSocket = new DatagramSocket(1234);
            // Vetor de byte para receber dados do pacote de broadcast
            byte[] receiveData = new byte[2048];
            // -----------------------------------------------------------------------
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
