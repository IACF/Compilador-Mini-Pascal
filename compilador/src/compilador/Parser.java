/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author victor
 */
public class Parser {
    private Token currentToken;
    private final Scanner scanner;
    
    public Parser() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("ex.txt"));
        this.scanner = new Scanner(reader);
        this.currentToken = scanner.scan();
        
        this.parsePrograma();
    }
    
    private void accept(String expectedToken) throws IOException{
        System.out.println(this.currentToken.spelling);
        if(this.currentToken.kind == scanner.map.get(expectedToken)){
            currentToken = scanner.scan();
        }else{
            System.out.println(this.currentToken.kind);
            System.out.println(scanner.map.get(expectedToken));
            throw new Error(currentToken);
        }
    }
    
    private void acceptIt() throws IOException{
        System.out.println(this.currentToken.spelling);
        currentToken = scanner.scan();
    }
    
    private void parsePrograma() throws IOException{
        accept("program");
        accept("id");
        accept(";");
        parseCorpo();
        accept(".");
        System.out.println("funfou");
    }
    
    private void parseCorpo() throws IOException{
        parseDeclaracoes();
        parseComandoComposto();     
    }
    
    private void parseDeclaracoes() throws IOException{
        System.out.println(this.currentToken.kind);
        System.out.println(scanner.map.get("begin"));
        while(this.currentToken.kind != scanner.map.get("begin")){
            parseDeclaracaoDeVariavel();
            accept(";");
        }
    }
    
    private void parseDeclaracaoDeVariavel() throws IOException{
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
           throw new Error(currentToken);
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
            this.currentToken.kind == scanner.map.get("float-lit") ||
             this.currentToken.kind == scanner.map.get("bool-lit")) 
        {
            acceptIt();
        } else {
            
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
        while(this.currentToken.kind == scanner.map.get("+") 
            || this.currentToken.kind == scanner.map.get("-") 
            || this.currentToken.kind == scanner.map.get("or"))
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
        while(this.currentToken.kind != scanner.map.get("*")
            || this.currentToken.kind != scanner.map.get("/")
            || this.currentToken.kind != scanner.map.get("and"))
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
        if(this.currentToken.kind == scanner.map.get("id")){
            parseVariavel();
        }else{
            if(this.currentToken.kind == scanner.map.get("(")){
                parseExpressao();
            }else{
                if(this.currentToken.kind == scanner.map.get("bool-lit")
                   || this.currentToken.kind == scanner.map.get("float-lit")
                   || this.currentToken.kind == scanner.map.get("bool-lit"))
                {
                    parseLiteral();
                }else{
                    throw new Error(currentToken);
                }
            }
        }
    }
    
    private void parseVariavel() throws IOException{
        accept("id");
        parseSeletor();
    }

}
