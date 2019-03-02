/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor
 */
public class tabelaDeIdentificacao {
    
    Map<String, elementoTabelaDeIdentificacao> table;

    public tabelaDeIdentificacao() {
        table = new HashMap<>();
    }
    
    public boolean insert(identificadorSimples i, Tipo t){
        
        if(table.containsKey(i.TK.spelling)){
             throw new Error("variavel já declarada.");
        }
        
        elementoTabelaDeIdentificacao element = new elementoTabelaDeIdentificacao(i, false, t);
        table.put(i.TK.spelling, element);
        return true;
        
    }
    
    public elementoTabelaDeIdentificacao retrieve(String variavel){
        if(table.containsKey(variavel)){
            elementoTabelaDeIdentificacao element = table.get(variavel);
            element.checker = true;
            return element;
        }
        
        return null;
    }
    
}
