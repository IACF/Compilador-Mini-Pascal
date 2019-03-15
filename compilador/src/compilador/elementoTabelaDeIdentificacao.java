/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author victor
 */
public class elementoTabelaDeIdentificacao {
    identificadorSimples id;
    boolean checker;
    Tipo tipo;
    int enderecoVariavel;
    
    public elementoTabelaDeIdentificacao(identificadorSimples id, boolean checker, Tipo tipo, int enderecoVariavel) {
        this.id = id;
        this.checker = checker;
        this.tipo = tipo;
        this.enderecoVariavel = enderecoVariavel;
    }
    
    
}
