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
public class declaracaoSequencial extends Declaracao{
    Declaracao D1;
    Declaracao D2;

    public declaracaoSequencial(Declaracao D1, Declaracao D2) {
        this.D1 = D1;
        this.D2 = D2;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorDeclaracaoSequencial(this);
    }
}
