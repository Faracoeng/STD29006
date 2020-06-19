package std.client;


// A classe Cliente é responsável por invocar os métodos remotos do Servidor.
// Ao executar o cliente é necessário informar o nome (ou IP) e porta
// da máquina onde o rmiregistry está sendo executado.

import std.InventarioDistribuido;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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


        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}