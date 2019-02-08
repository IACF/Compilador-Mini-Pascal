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
public class idSequencial extends Identificador{
    Identificador I1;
    Identificador I2;

    public idSequencial(Identificador I1,Identificador I2) {
        this.I1 = I1;
        this.I2 = I2;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorIdSequencial(this);
    }
    
}
