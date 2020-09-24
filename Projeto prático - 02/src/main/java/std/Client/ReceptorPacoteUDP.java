package std.Client;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class ReceptorPacoteUDP extends Thread {

    private ArrayList<String[]> listaRecebida;
    private int tempoRecebido;
    private static ProcessoWorker obj;
    private String idDoWorker;
    public boolean terminei = false;
    DataOutputStream fluxoSaida;

    @Override
    public void run() {

        int i = 0;
        for (String[] evento : this.listaRecebida) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("Leu do arquivo: " + evento[0]+"_"+evento[1]);
            obj.adicionarEventoNaFila(evento[0]+"_"+evento[1]);
            // quando tiver processado a ultima linha dira que terminou
            if (i == this.listaRecebida.size() - 1) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    this.fluxoSaida.writeUTF("terminei");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }
    }

}
