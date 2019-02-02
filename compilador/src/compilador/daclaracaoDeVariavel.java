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
public class daclaracaoDeVariavel extends Declaracao {
    Identificador I;
    Tipo T;

    public daclaracaoDeVariavel(Identificador I, Tipo T) {
        this.I = I;
        this.T = T;
    }
    
}
