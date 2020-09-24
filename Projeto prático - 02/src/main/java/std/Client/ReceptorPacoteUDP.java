package std.Client;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class ReceptorPacoteUDP {

    private ArrayList<String[]> listaRecebida;
    private int tempoRecebido;
    private static ProcessoWorker obj;
    private String idDoWorker;
    public boolean terminei = false;
    DataOutputStream fluxoSaida;
}
