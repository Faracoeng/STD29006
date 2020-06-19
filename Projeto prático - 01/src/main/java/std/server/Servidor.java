package std.server;

// Servidor deverá enviar um pacotes UDP na rede com broadcast
// contendo seu IP para que as RaspBerry PI consigam se
// conectar estabelendo conexão TCP

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Servidor {

    // descobre ip da máquina com aplicativo servidor
    static public String getIPV4Semlo() {
        // NetworkInterface.getNetworkInterfaces() retorna Enumeration<NetworkInterface>
        String elementos = "";
        Enumeration nis = null;
        try {
            nis = NetworkInterface.getNetworkInterfaces(); // retorna todas as interfaces de rede da máquina para
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (nis.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) nis.nextElement();
            // procura uma interface de rede que tenha o endereço IP especificado.
            Enumeration ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = (InetAddress) ias.nextElement();
                if (!ni.getName().equals("lo")) // remove interface de loopback
                    elementos = ia.getHostAddress();
            }
        }
        return elementos;
    }

    public static void main(String[] args) {

    }
}
