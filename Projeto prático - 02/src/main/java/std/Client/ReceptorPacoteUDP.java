package std.Client;


import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceptorPacoteUDP extends Thread {

    private int porta;
    private String mensagemDeOutroWorker;
    private boolean mensageRcebida = false;
    private static ProcessoWorker obj;

    public ReceptorPacoteUDP(int porta, ProcessoWorker obj) {
        this.porta = porta;
        ReceptorPacoteUDP.obj = obj;
    }

    public int getPorta() {
        return porta;
    }

    public String getMensagemDeOutroWorker() {
        return mensagemDeOutroWorker;
    }

    @Override
    public void run() {
        try {
            DatagramSocket servidor = new DatagramSocket(this.porta);
            byte[] dadosRecebidos = new byte[2048];
            //byte[] dadosEnviados;
            //System.out.println("ouvindo na porta " + this.porta);

            while (true) {
                DatagramPacket pacoteRecebido = new DatagramPacket(dadosRecebidos, dadosRecebidos.length);
                servidor.receive(pacoteRecebido);

                mensagemDeOutroWorker = new String(pacoteRecebido.getData());
                //System.out.println("Recebido -------> " + mensagemDeOutroWorker);
                obj.receberUDPColocarNaFila(mensagemDeOutroWorker);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
