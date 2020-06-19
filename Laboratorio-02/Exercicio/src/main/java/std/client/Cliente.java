package std.client;


// A classe Cliente é responsável por invocar os métodos remotos do Servidor.
// Ao executar o cliente é necessário informar o nome (ou IP) e porta
// da máquina onde o rmiregistry está sendo executado.

import std.InventarioDistribuido;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    private static String nomeServidor;
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuInventario";

    public static void main(String[] args) {
        try {
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null) {
                nomeServidor = args[0];
            }
            if (args[1] != null) {
                porta = Integer.parseInt(args[1]);
            }

            System.out.println("> Conectando no servidor " + nomeServidor);
            // Obtendo referencia do servico de registro
            Registry registro = LocateRegistry.getRegistry(nomeServidor, porta);
            // Procurando pelo objeto distribuido registrado previamente com o NOMEOBJDIST
            InventarioDistribuido stub = (InventarioDistribuido) registro.lookup(NOMEOBJDIST);

            // Invocando os metodos do objeto distribuıdo
            // -----------------------------------------------------------------------------
            if (args.length > 3) {  // Quantidade de argumentos para
                // este caso de uso do servidor
                // que sera adicionar novo AP
                if (args[1].equals("add")) {
                    System.out.println("> Adicionando AP ao inventário");
                    stub.adicionaAP(args[2], args[3], args[4], args[5]);

                    // args[2] = String nome
                    // args[3] = String mac
                    // args[4] = String freq
                    // args[5] = String local

                } else if (args.length == 3) {  // contempla os casos de
                    // Listar todos os APs que operam com a frequência de 5Ghz.
                    // Remover AP com o identificador AP2
                    if (args[1].equals("list")) {
                        if (args[2].equals("freq:5")) {
                            ArrayList<String> listaDeNomes5G = stub.listarFrequencia5G("freq:5");
                            System.out.println("APs que operam com a frequencia de 5GHz: ");
                            for (String ap : listaDeNomes5G) {
                                System.out.println(ap.toString());
                            }
                        }
                    } else if (args[1].equals("del")) {
                        System.out.println("AP do inventário removido ");
                        stub.removeAP(args[2]);
                    }

                } else if (args[1].equals("list")) { // Caso de listar todos os APs

                    ArrayList<String> listaTotalAPs = stub.listarInventario();
                    System.out.println("APs cadastrados: ");

                    for (String lista : listaTotalAPs) {
                        System.out.println(lista.toString());
                    }

                } else {
                    System.out.println("Erro!");
                }
                // -----------------------------------------------------------------------------


                System.out.println("Fim da execucao do cliente!");
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}