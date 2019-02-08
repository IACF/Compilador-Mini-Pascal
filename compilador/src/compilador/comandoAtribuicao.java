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
public class comandoAtribuicao extends Comando {
    Variavel V;
    Expressao E;

    public comandoAtribuicao(Variavel V, Expressao E) {
        this.V = V;
        this.E = E;
    }
    
    @Override
    public void visit (Visitor v) {
        v.visitorComandoAtribuicao(this);
    }
}
