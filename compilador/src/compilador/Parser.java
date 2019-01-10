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
            throw new Error(expectedToken, currentToken);
        }
    }
    
    private void acceptIt() throws IOException{
        currentToken = scanner.scan();
    }
    
    private void parseDeclaracoes() throws IOException{
        while(this.currentToken.kind != scanner.map.get("begin")){
            parseDeclarcaoDeVariavel();
            accept(";");
        }
    }
    
    private void parseDeclarcaoDeVariavel() throws IOException{
        accept("var");
        parseListaDeIds();
        accept(":");
        parseTipo();
    }
    
    private void parseListaDeIds() throws IOException{
        accept("id");
        while(this.currentToken.kind == scanner.map.get(",")){
            accept(",");
            accept("id");
        }
    }
    
    private void parseTipo() throws IOException{
//        if(this.currentToken.kind == scanner.map.get("array")){
//            parseTipoAgregado();
//        } else if(this.currentToken ) {
//            
//        }
        
        
    }
    
    private void parseTipoAgregado() throws IOException{
        accept("array");
        accept("[");
        parseLiteral();
        accept("..");
        parseLiteral();
        accept("[");
        accept("of");
        parseTipo();



    }
    
    private void parseLiteral() throws IOException{
        
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
            return;
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
                return;
            }else{
                if(scanner.map.get("while") == currentToken.kind){
                    acceptIt();
                    parseExpressao();
                    accept("do");
                    parseComando();
                    return;
                }else{
                    if(scanner.map.get("begin") == currentToken.kind){
                        parseComandoComposto();
                        return;
                    }
                }
            } 
        }
        
      throw new Error(currentToken);
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
