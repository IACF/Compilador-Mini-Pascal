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
public class Operador extends AST{
    Token TK;

    public Operador(Token TK) {
        this.TK = TK;
    }
    
}
