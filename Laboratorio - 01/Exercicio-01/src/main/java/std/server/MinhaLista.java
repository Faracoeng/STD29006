package std.server;

import java.util.ArrayList;

// Esta classe representa uma lista que cada criente criará

public class MinhaLista {

    private ArrayList<String> lista;
    private String id;

//      criar uma nova lista

    public MinhaLista(String nomeDado) {
        this.lista = new ArrayList<>();
        this.id = nomeDado;
    }
    // Poder identificar a lista, pode dar erro caso tenham duas lista com o mesmo nome
    public String getNome() {
        return id;
    }
    //  obter o último valor adicionado em uma lista, e por consequência, removê-lo dessa lista.

    public String obterUltimoValor() {
        return lista.remove(lista.size() - 1);
    }
    //      adicionar valores em uma lista já existente,
    public void setConteudo(String conteudo) {
        lista.add(conteudo);
    }

    public String toString(){
        String aux = "";
        for(String str: lista){
            aux = aux + " "+ str.toString();
        }
        return aux;
    }
}
