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
public class identificationTableElement {
    identificadorSimples id;
    boolean checker;
    Tipo tipo;

    public identificationTableElement(identificadorSimples id, boolean checker, Tipo tipo) {
        this.id = id;
        this.checker = checker;
        this.tipo = tipo;
    }
    
    
}
