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
public abstract class Expressao extends AST{
    String tipo;
    
    public abstract void visit (Visitor v);
    
}
