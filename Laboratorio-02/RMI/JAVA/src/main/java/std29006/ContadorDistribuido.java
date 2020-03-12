package std29006;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContadorDistribuido extends Remote {
        public void incrementa() throws RemoteException;
        public int obtemValorAtual()  throws RemoteException;
}

