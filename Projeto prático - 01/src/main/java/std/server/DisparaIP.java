package std.server;


// CLASSE responsável por disparar IP do
// servidor na rede idependentemente do fluxo
// principal do programa através do uso de Threads

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

// Esta classe dispara IP do servidor por broadcast na rede, sempre em uma Thread
// diferente pois no fluxo principal, o servidor deverá aguarda alguma solicitação
// de conexao TCP proveniente das Raspberry PI

public class DisparaIP extends Thread{
    String IPServidor;
    InetAddress enderecoBroadcast;
    private boolean statusBroadcast = true;

    public DisparaIP(String ip, InetAddress endereco){
        this.IPServidor = ip;
        this.enderecoBroadcast = endereco;
    }

    // Método que dispara IP do servidor na rede
    public void broadcast(String mensagem, InetAddress endereco) throws IOException {

        DatagramSocket socket = new DatagramSocket();
        // buffer para arazenar IP do servidor e ser enviado no datagrama
        byte[] buffer = mensagem.getBytes();
        // enviando pacote UDP para endereços de broadcast na porta 1234,
        // na qualtodo cliente o estará aguardando ao ser iniciado
        DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, endereco, 1234);
        // enviando o pacote
        socket.setBroadcast(true);
        socket.send(pacote);
        socket.close();
    }

    public void interromperBroadcast() {
        this.statusBroadcast = false;
    }

    public void run() {

        while (statusBroadcast) {
            try {
                broadcast(IPServidor, enderecoBroadcast);
                // disparar broadcast apenas a cada 1 segunda para não travar a rede
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        // System.out.println("parou a thread de broadcast");
        this.statusBroadcast = true;
    }

}
