package std.server;

// A classe Servidor é responsável por instanciar um objeto da
// classeContador, subir umserviço de nomes (rmiregistry)
// e registrar a instância da classe Contador no serviço de nomes.

import std.InventarioDistribuido;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    // Constantes que indicam onde esta sendo executado o servico de registro,
    // qual porta e qual o nome do objeto distribuıdo

    private static String nomeServidor = "127.0.0.1";
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuContador";

    public static void main(String[] args) {
        try {
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null) {
                nomeServidor = args[0];
            }
            // recebendo porta do rmiregistry por argumento de linha de comando
            if (args[1] != null) {
                porta = Integer.parseInt(args[1]);

                // Criando
                Inventario c = new Inventario();
                // Definindo o hostname do servidor
                System.setProperty("java.rmi.std29006.server.hostname", nomeServidor);
                InventarioDistribuido stub = (InventarioDistribuido)
                        UnicastRemoteObject.exportObject(c, 0);
                // Criando serviço de registro
                Registry registro = LocateRegistry.createRegistry(porta);

                // Registrando objeto distribuido
                registro.bind(NOMEOBJDIST, stub);

                System.out.println("Servidor pronto!\n");

                // teste
                //c.adicionaAP("AP1" ,"mac:abcdef" ,"freq:2.4:5", "Lab SiDi");

                System.out.println("Pressione CTRL + C para encerrar...");
            }
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
