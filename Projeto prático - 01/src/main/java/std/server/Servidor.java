package std.server;

// Servidor deverá enviar um pacotes UDP na rede com broadcast
// contendo seu IP para que as RaspBerry PI consigam se
// conectar estabelendo conexão TCP

import java.io.IOException;
import java.net.*;
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
        try{
            // Variavel utilizada para verificar a quantidade de conexoes ativas no sistema
            int numeroDeConexoes = 0;
            // vetor de Threads responsável por guardar as conexoes realizadas com os clientes
            Thread procesos[] = new Thread[4];
            // Thread responsável por disparar IP do servidor por broadcast na rede
            Thread broadcast = new DisparaIP(getIPV4Semlo(), InetAddress.getByName("255.255.255.255"));

            // Justificativa:
            // --------------------------------------------------------------------------------
            // getByName() Determina o endereço IP de um host, dado o nome do host, no caso
            // 255.255.255.255 que é uma representacao textual do endereco de broadcast
            // 255.255.255.255 Endereço de broadcast. Todos os dispositivos conectados
            // a rede estão habilitados a receber datagramas.
            // Uma mensagem enviada para um endereço de broadcast
            // pode ser recebido por todos os hospedeiros conectados à rede.
            // ---------------------------------------------------------------------------------

            /* Após realizar broadcast, registra servico na porta 1235 e aguarda por conexoes */
            ServerSocket servidor = new ServerSocket(4321);
            // dispara broadcast na rede
            broadcast.start();
            // loop que deve ser executado 4 vezes, uma vez para cada conexao
            while (numeroDeConexoes < 4) {



            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
