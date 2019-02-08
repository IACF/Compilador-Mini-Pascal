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
public class tipoSimples extends Tipo{
    Token TK;

    public tipoSimples(Token TK) {
        this.TK = TK;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorTipoSimples(this);
    }
}
