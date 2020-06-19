package std.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static Gerenciador agenda = new Gerenciador();
    public static final String MENU = "\n         =========================================================" +
            "\n        |     1 - Criar nova lista                              |" +
            "\n        |     2 - adicionar valores em uma lista já existente   |" +
            "\n        |     3 - remover ultimo valor de uma lista             |" +
            "\n        |     4 - sair                                          |";

    public static void main(String[] args) throws IOException {
        /* Registra servico na porta 1234 e aguarda por conexoes */
        ServerSocket servidor = new ServerSocket(1234);

        System.out.println("Aguardando por conexoes");

        Socket conexao = servidor.accept();
        System.out.println("Cliente conectou " + conexao);

        /* Estabelece fluxos de entrada e saida */
        DataInputStream fluxoEntrada = new DataInputStream(new BufferedInputStream(conexao.getInputStream()));
        DataOutputStream fluxoSaida = new DataOutputStream(conexao.getOutputStream());

        /* inicia a comunicacao */
        String mensagem = fluxoEntrada.readUTF();
        System.out.println("Cliente > " + mensagem);
        fluxoSaida.writeUTF("Oi, eu sou o servidor!");
//------------------------------------------------------------------------------

        //Menu oferecido pelo servidor
        int opcao = 0;
        String id = "";
        String dado = "";

        agenda.criarLista("teste");
        agenda.addValor("teste","primerioValor");
        fluxoSaida.writeUTF(MENU);
        do {
            String resposta = fluxoEntrada.readUTF();
            System.out.println(">>> Resposta do cliente: "+resposta);
            opcao = Integer.parseInt(resposta); // converte string do cliente em um int

            switch (opcao) {
                // Criar nova lista
                case 1 :
                    fluxoSaida.writeUTF("Qual id de sua lista?");
                    id = fluxoEntrada.readUTF();
                    // System.out.println("servidor -> "+id);
                    if(agenda.criarLista(id)) fluxoSaida.writeUTF("lista criada com sucesso" + MENU);
                    else fluxoSaida.writeUTF("erro");
                    break;

                //  adicionar valores em uma lista já existente
                case 2:
                    fluxoSaida.writeUTF("Qual id da lista que deseja inserir o valor?");
                    id = fluxoEntrada.readUTF();
                    System.out.println("servidor "+ id);
                    fluxoSaida.writeUTF("Qual valor deseja inserir?");
                    dado = fluxoEntrada.readUTF();
                    if(agenda.addValor(id,dado)) fluxoSaida.writeUTF("Valor: "+dado+" inserido com sucesso na lista: "+id  + MENU);
                    break;
                // remover ultimo valor
                case 3:
                    fluxoSaida.writeUTF("Qual id da lista que deseja remover o valor?");
                    id = fluxoEntrada.readUTF();
                    fluxoSaida.writeUTF(agenda.removerValor(id) + MENU);
                    break;
                // sair
                case 4:
                    fluxoSaida.writeUTF("Até mais...");
                    break;
                default:
                    fluxoSaida.writeUTF("Opção inválida" + MENU);
                    break;
            }

        } while (opcao != 4);

//------------------------------------------------------------------------------




        /* Fecha fluxos e socket */
        fluxoEntrada.close();
        fluxoSaida.close();
        conexao.close();
        servidor.close();
    }
}
