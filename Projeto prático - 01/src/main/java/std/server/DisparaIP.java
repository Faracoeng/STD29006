package std.server;


// CLASSE responsável por disparar IP do
// servidor na rede idependentemente do fluxo
// principal do programa através do uso de Threads

import java.net.InetAddress;

public class DisparaIP extends Thread{
    String IPServidor;
    InetAddress enderecoBroadcast;
    private boolean statusBroadcast = true;

    public DisparaIP(String ip, InetAddress endereco){
        this.IPServidor = ip;
        this.enderecoBroadcast = endereco;
    }

}
