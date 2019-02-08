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
public class expressaoSequencial extends Expressao {
    Expressao E1;
    Expressao E2;

    public expressaoSequencial(Expressao E1, Expressao E2) {
        this.E1 = E1;
        this.E2 = E2;
    }
   
    @Override
    public void visit (Visitor v) {
        v.visitorExpressaoSequencial(this);
    }
}
