package std.server;


// CLASSE responsável por disparar IP do
// servidor na rede idependentemente do fluxo
// principal do programa através do uso de Threads

import java.net.InetAddress;

public class DisparaIP extends Thread{

    public DisparaIP(String ipv4Semlo, InetAddress byName) {
    }
}
