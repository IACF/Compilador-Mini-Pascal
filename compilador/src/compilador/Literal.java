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
public class Literal extends AST{
    Token T;

    public Literal(Token T) {
        this.T = T;
    }
       
}