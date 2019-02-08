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
public class declaracaoDeVariavel extends Declaracao {
    Identificador I;
    Tipo T;

    public declaracaoDeVariavel(Identificador I, Tipo T) {
        this.I = I;
        this.T = T;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorDeclaracaoDeVariavel(this);
    }
    
}
