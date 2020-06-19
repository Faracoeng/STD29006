package std.server;

// A classe Servidor é responsável por instanciar um objeto da
// classeContador, subir umserviço de nomes (rmiregistry)
// e registrar a instância da classe Contador no serviço de nomes.

public class Servidor {

    // Constantes que indicam onde esta sendo executado o servico de registro,
    // qual porta e qual o nome do objeto distribuıdo

    private static String nomeServidor = "127.0.0.1";
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuContador";
}
