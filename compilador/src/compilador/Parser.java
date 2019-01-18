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
    
    private void parsePrograma() throws IOException{
        accept("program");
        accept("id");
        accept(";");
        parseCorpo();
        accept(".");
    }
    
    private void parseCorpo() throws IOException{
        parseDeclaracoes();
        parseComandoComposto();     
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
        if(this.currentToken.kind == scanner.map.get("array")){
            parseTipoAgregado();
        } else {
            parseTipoSimples();
        }
    }
    
    private void parseTipoSimples() throws IOException{
        if (
            this.currentToken.kind == scanner.map.get("integer") 
            || this.currentToken.kind == scanner.map.get("real") 
            || this.currentToken.kind == scanner.map.get("boolean")
        ) {
            acceptIt();
        } else {
            System.out.println("Erro");
        }
            
    }
    
    private void parseTipoAgregado() throws IOException{
        accept("array");
        accept("[");
        parseLiteral();
        accept("..");
        parseLiteral();
        accept("]");
        accept("of");
        parseTipo();
    }
    
    private void parseLiteral() throws IOException{
        if(
            this.currentToken.kind == scanner.map.get("int-lit") ||
            this.currentToken.kind == scanner.map.get("float-lit")
        ) {
            acceptIt();
        } else {
            parseBooleanLiteral();
        }
    }
    
    private void parseBooleanLiteral() throws IOException{
        if(
            this.currentToken.kind == scanner.map.get("true") ||
            this.currentToken.kind == scanner.map.get("false")
        ) {
            acceptIt();
        } else {
            System.out.println("error");
        }
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

    private void parseExpressaoSimples() throws IOException
    {
        parseTermo();
        while(this.currentToken.kind != scanner.map.get("<")
            && this.currentToken.kind != scanner.map.get(">")
            && this.currentToken.kind != scanner.map.get("<=")
            && this.currentToken.kind != scanner.map.get(">=")
            && this.currentToken.kind != scanner.map.get("=")
            && this.currentToken.kind != scanner.map.get("<>"))
        {
            parseOpAd();
            parseTermo();
        }  
    }

    private void parseOpAd() throws IOException{
        if (
            this.currentToken.kind == scanner.map.get("+") 
            || this.currentToken.kind == scanner.map.get("-") 
            || this.currentToken.kind == scanner.map.get("or")
        ) {
            acceptIt();
        } else {
            System.out.println("Erro");
        }
    }
    
    private void parseTermo() throws IOException{
        parseFator();
        while(this.currentToken.kind != scanner.map.get("+")
            && this.currentToken.kind != scanner.map.get("-")
            && this.currentToken.kind != scanner.map.get("or"))
        {
            parseOpMul();
            parseFator();
        }  
    }
    
    private void parseOpMul() throws IOException{
        if (
            this.currentToken.kind == scanner.map.get("*") 
            || this.currentToken.kind == scanner.map.get("/") 
            || this.currentToken.kind == scanner.map.get("and")
        ) {
            acceptIt();
        } else {
            System.out.println("Erro");
        }
    }
    
    private void parseFator() throws IOException{
        
    }
    
    private void parseVazio() throws IOException{
    
    }

}
