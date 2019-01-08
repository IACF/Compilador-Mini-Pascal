/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.IOException;

/**
 *
 * @author victor
 */
public class Parser {
    private Token currentToken;
    private Scanner scanner;
    
    private void accept(String expectedToken) throws IOException{
        if(this.currentToken.kind == scanner.map.get(expectedToken)){
            currentToken = scanner.scan();
        }else{
            //reportar erro de sintaxe.
        }
    }
    
    private void acceptIt() throws IOException{
        currentToken = scanner.scan();
    }
    
    private void parseComandoComposto() throws IOException{
        accept("begin");
        parseListaDeComandos();
        accept("end");
    }

    private void parseListaDeComandos() throws IOException {
        while(this.currentToken.kind != scanner.map.get("end")){ // end é o follow de lista de comando.
            parseComando();
            accept(";");
        }
    }

    private void parseComando() throws IOException {
        if(scanner.map.get("id") == currentToken.kind){
            acceptIt();
            parseSeletor();
            accept(":=");
            parseExpressao();
        }else{
            if(scanner.map.get("if") == currentToken.kind){
                acceptIt();
                parseExpressao();
                accept("then");
                parseComando();
                while(currentToken.kind == scanner.map.get("else")){
                    acceptIt();
                    parseComando();
                }
            }else{
                if(scanner.map.get("while") == currentToken.kind){
                    acceptIt();
                    parseExpressao();
                    accept("do");
                    parseComando();
                }else{
                    if(scanner.map.get("begin") == currentToken.kind)
                        parseComandoComposto();
                }
            } 
        }
   }
    
    private void parseSeletor() throws IOException {
        
        while(this.currentToken.kind == scanner.map.get("[")){
            acceptIt();
            parseExpressao();
            accept("]");
        }
    }

    private void parseExpressao() throws IOException {
        parseExpressaoSimples();
        if(this.currentToken.kind >= scanner.map.get("<") && this.currentToken.kind <= scanner.map.get("<>")){
            acceptIt();
            parseExpressaoSimples();  
        }
        
    }

    private void parseExpressaoSimples() {
        
    }
}
