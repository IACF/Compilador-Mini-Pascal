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
public class expressaoUnaria extends Expressao{
    Operador O;
    Expressao E;

    public expressaoUnaria(Operador O, Expressao E) {
        this.O = O;
        this.E = E;
    }
    
}
