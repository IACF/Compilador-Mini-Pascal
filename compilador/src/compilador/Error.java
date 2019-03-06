/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

public class Error extends RuntimeException{
    public Error(String expected, Token current){
        super("Esperava-se '" + expected + "' encontrado '" + current.spelling + "', linha = " + current.line + " coluna = " + current.collum);
    }
    
    public Error(Token current){
        super("Error de sintaxe na linha " + current.line + " coluna " + current.collum);
    }
    
    public Error(String string){
        super(string);
    }
    
    public Error(identificadorSimples i){
        super("variavel " + i.TK.spelling + " não declarada.");
    }
    
    public Error(String mensagem, identificadorSimples i){
        super(i.TK.spelling + mensagem + "na linha " + i.TK.line + ", coluna " + i.TK.collum + ".");
    }
        
    public Error(String mensagem, Operador op){
        super(mensagem + op.TK.spelling + " na linha " + op.TK.line + ".");
    }
    
    
}
