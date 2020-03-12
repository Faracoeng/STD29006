package std29006.server;
import std29006.ContadorDistribuido;

import java.rmi.RemoteException;

public class Contador implements ContadorDistribuido {
    private int valor = 0;
    @Override
    public void incrementa() throws RemoteException {
        this.valor++;
    }
    @Override
    public int obtemValorAtual() throws RemoteException {return this.valor;}
}

