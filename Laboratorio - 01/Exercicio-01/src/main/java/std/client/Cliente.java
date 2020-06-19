package std.client;
// ok
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {

        Scanner teclado = new Scanner(System.in);
        int porta = 1234;
        String ip = "127.0.0.1"; // loopback para testar na própria máquina

        /* Estabele conexao com o servidor */
        Socket conexao = new Socket(ip,porta);
        System.out.println("Conectado! " + conexao);
        /* Estabelece fluxos de entrada e saida */
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());
        DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
        /* Inicia comunicacao */
        fluxoSaida.writeUTF("Oi, eu sou o cliente!");
        fluxoSaida.flush();
        String mensagem = fluxoEntrada.readUTF();
        System.out.println("Servidor > " + mensagem);

//-----------------------------------------------------------------------------
        /* Inicia comunicacao com menu*/
        String resposta = "";
        do {
            System.out.println(fluxoEntrada.readUTF()); // mensagem do servidor
            resposta = teclado.nextLine();
            // resposta cliente

            fluxoSaida.writeUTF(resposta);
            //System.out.println(opcao);
            fluxoSaida.flush();
            // Quando se invoca o flush(), significa enviar todos os conteúdos naquele momento.
            // ele é chamado implicitamente, não precisa realmente invocar esse método em todas as situações,
            // em ambiente simples provavelmente sempre vai funcionar sem o flush, agora em um ambiente mais complexo,
            // talvez os dados se percam no meio do caminho.
            // System.out.println(fluxoEntrada.readUTF());
        }while (!resposta.equals("4"));

        //System.out.println(fluxoEntrada.readUTF());
//-----------------------------------------------------------------------------
        /* Fecha fluxos e socket */
        fluxoSaida.close();
        fluxoEntrada.close();
        conexao.close();

    }
}
