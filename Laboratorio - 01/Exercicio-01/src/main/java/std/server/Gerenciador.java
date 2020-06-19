package std.server;

import java.util.HashMap;

//Essa classe deve estruturar toda lista criado em uma HashMap,
// para associar cada cliente a sua respectiva lista, evita problemas como
// cliente criar 2 listas com mesmo nome

public class Gerenciador {

    private MinhaLista l;
    private HashMap<String, MinhaLista> listas;

    public Gerenciador() {
        listas = new HashMap<>();
    }
    // criar uma nova lista,
    public boolean criarLista(String nomeLista){
        if(!listas.containsKey(nomeLista)) {
            listas.put(nomeLista, new MinhaLista(nomeLista));
            return true;
        }else return false;
    }
    //  adicionar valores em uma lista já existente

    public boolean addValor(String nomeLista, String valor){
        if(listas.containsKey(nomeLista)){
            listas.get(nomeLista).setConteudo(valor);
            return true;
        }else return false;
    }
    //  obter o último valor adicionado em uma lista, e por consequência, removê-lo dessa lista.
    public String removerValor(String nomeLista){
        if(listas.containsKey(nomeLista)){
            return listas.get(nomeLista).obterUltimoValor();

        }else return "erro";

    }
}
