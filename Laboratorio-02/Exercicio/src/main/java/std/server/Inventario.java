package std.server;

import std.InventarioDistribuido;

import java.rmi.RemoteException;
import java.util.ArrayList;

// A classe Inventario implementará a interface
// InventarioDistribuido

public class Inventario implements InventarioDistribuido {
    private ArrayList<String> inventario = new ArrayList<>();
    private String accessPoint;


    @Override
    //   Adicionar um AP em um ArrayList
    public void adicionaAP(String nome, String mac, String freq, String local) {
        this.accessPoint = nome + " " + mac + " " + freq + " " + local;
        this.inventario.add(accessPoint);
    }

    @Override
    // Retornar lista com AP cadastrados
    public ArrayList<String> listarInventario() throws RemoteException {
        return null;
    }

    @Override
    // Remover um AP da lista de cadastrados
    public void removeAP(String ap) throws RemoteException {
        int i = 0;
        for (String str : inventario) {
            if(str.contains(ap)){
                inventario.remove(i);
                i += 1; // pode dar erro aqui
            }
        }
    }

    @Override
    // Retornar uma lista de APs frequência de operação
    public ArrayList<String> listarFrequencia5G(String s) throws RemoteException {
        return inventario;
    }
}
