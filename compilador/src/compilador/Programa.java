package compilador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author victor
 */
public class Programa extends AST{
    public Identificador I;
    public Corpo C;
    
    Programa(Identificador I, Corpo C){
        this.I = I;
        this.C = C;
    }
    
    public void visit (Visitor v){
        v.visitorPrograma(this);
    }
}
