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
public class comandoIterativo extends Comando{
    Expressao E;
    Comando C;

    public comandoIterativo(Expressao E, Comando C) {
        this.E = E;
        this.C = C;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorComandoIterativo(this);
    }
}