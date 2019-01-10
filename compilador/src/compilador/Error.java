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
public class Error extends RuntimeException{
    public Error(String expected, Token current){
        super("Esperava-se " + expected + " linha = " + current.line + " coluna = " + current.collum);
    }
}
