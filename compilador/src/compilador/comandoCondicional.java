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
public class comandoCondicional extends Comando{
    Expressao E;
    Comando C1;
    Comando C2;

    public comandoCondicional(Expressao E, Comando C1, Comando C2) {
        this.E = E;
        this.C1 = C1;
        this.C2 = C2;
    
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorComandoCondicional(this);
    }
}
