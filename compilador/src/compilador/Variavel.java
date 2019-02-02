/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.beans.Expression;

/**
 *
 * @author victor
 */
public class Variavel extends AST{
    Identificador I;
    Expressao E;

    public Variavel(Identificador I, Expressao E) {
        this.I = I;
        this.E = E;
    }
    
}
