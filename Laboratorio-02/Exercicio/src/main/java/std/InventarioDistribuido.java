package std;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

// A  interface InventarioDistribuido possui  os
// métodos que serão aplicados. Esse arquivo deve ser
// disponibilizado para os desenvolvedores do
// aplicativo Servidor e do aplicativo Cliente

public class InventarioDistribuido extends Remote {

    public void adicionaAP(String nome, String mac, String freq, String local) throws RemoteException;
    public ArrayList<String> listarInventario() throws RemoteException;
    public void removeAP(String ap) throws RemoteException;
    public ArrayList<String> listarFrequencia5G(String s) throws RemoteException;
}
