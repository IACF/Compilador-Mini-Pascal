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
public class comandoComposto extends Comando {
    Comando C1;
    Comando C2;

    public comandoComposto(Comando C1, Comando C2) {
        this.C1 = C1;
        this.C2 = C2;
    }
    
}
