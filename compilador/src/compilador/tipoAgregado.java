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
public class tipoAgregado extends Tipo {
    Literal L1;
    Literal L2;
    Tipo T;

    public tipoAgregado(Literal L1, Literal L2, Tipo T) {
        this.L1 = L1;
        this.L2 = L2;
        this.T = T;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorTipoAgregado(this);
    }
}
