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
public class Corpo extends AST {
    public Declaracao D;
    public Comando C;
    
    Corpo(Declaracao D, Comando C){
        this.D = D;
        this.C = C;
    }
    
}
