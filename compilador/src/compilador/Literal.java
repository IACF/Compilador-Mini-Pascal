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
public class Literal extends Expressao{
    Token TK;
    
    public Literal(Token TK) {
        this.TK = TK;
        if(this.TK.kind == 42 ||  this.TK.kind == 43) {
            this.tipo = "boolean";
        }   
        if(this.TK.kind == 11) {
            this.tipo = "integer";
        }
        if(this.TK.kind == 37) {
            this.tipo = "real";
        }
    }
     
    @Override
    public void visit (Visitor v) {
        v.visitorLiteral(this);
    }
}
