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
        // parte 2 do projeto
        int numeroDeTrabalhadores = 0;
        int linhasMatrizA = 0;
        int colunasMatrizA = 0;
        int linhasMatrizB = 0;
        int colunasMatrizB = 0;
        int semente = 0;

        try {
            numeroDeTrabalhadores = Integer.parseInt(args[0]);
            linhasMatrizA = Integer.parseInt(args[1]);
            colunasMatrizA = Integer.parseInt(args[2]);
            linhasMatrizB = Integer.parseInt(args[3]);
            colunasMatrizB = Integer.parseInt(args[4]);
            semente = Integer.parseInt(args[5]);

            //tratando possiveis erros
            if(colunasMatrizA != linhasMatrizB) System.out.println("Impossivel multiplicar matrizes");

        } catch (Exception e) {
            numeroDeTrabalhadores = 4;
            linhasMatrizA = 1000;
            colunasMatrizA = 1000;
            linhasMatrizB = 1000;
            colunasMatrizB = 1000;
            semente = 1000;
        }

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

                System.out.println("Aguardando por conexoes");
                // Aguarda cliente solicitar conexao TCP
                Socket conexao = servidor.accept();
                // Ao se conectar, conexao é salva no vetor de Threads
                procesos[numeroDeConexoes] = new ServidorThread(conexao);
                System.out.println(numeroDeConexoes + 1 + "º Raspbarry conectada");
                // Registra numero de conexões
                numeroDeConexoes += 1;
            }
            // Encerra Thread que dispara broadcast na rede pois todas as 4 conexões ja foram estabelecidas
            broadcast.stop();

            // dispara todas as Threads após estabelecer conexao com todas as Rasps
            for (Thread obj : procesos) {
                obj.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
