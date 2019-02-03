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
public class expressaoBinaria extends Expressao{
    Expressao E1;
    Operador O;
    Expressao E2;

    public expressaoBinaria(Expressao E1, Operador O, Expressao E2) {
        this.E1 = E1;
        this.O = O;
        this.E2 = E2;
    }
    
}
