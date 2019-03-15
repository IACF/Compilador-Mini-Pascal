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
public class Variavel extends Expressao{
    Identificador I;    
    Expressao E;
    elementoTabelaDeIdentificacao ponteiro;

    public Variavel(Identificador I, Expressao E) {
        this.I = I;
        this.E = E;
    }
    
    public void setPonteiroDeclaracao(elementoTabelaDeIdentificacao p){
        this.ponteiro = p;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorVariavel(this);
    }
}
