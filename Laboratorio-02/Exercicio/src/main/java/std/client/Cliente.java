package std.client;


// A classe Cliente é responsável por invocar os métodos remotos do Servidor.
// Ao executar o cliente é necessário informar o nome (ou IP) e porta
// da máquina onde o rmiregistry está sendo executado.

public class Cliente {

    private static String nomeServidor;
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuInventario";
}
