package std.server;

// A classe Servidor é responsável por instanciar um objeto da
// classeContador, subir umserviço de nomes (rmiregistry)
// e registrar a instância da classe Contador no serviço de nomes.

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    // Constantes que indicam onde esta sendo executado o servico de registro,
    // qual porta e qual o nome do objeto distribuıdo

    private static String nomeServidor = "127.0.0.1";
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuContador";

    public static void main(String[] args) {
        try{
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null) {
                nomeServidor = args[0];
            }
            // recebendo porta do rmiregistry por argumento de linha de comando
            if (args[1] != null) {
                porta = Integer.parseInt(args[1]);

        }catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
