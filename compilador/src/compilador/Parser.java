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
            throw new Error(expectedToken, currentToken);
        }
    }
    
    private void acceptIt() throws IOException{
        System.out.println(this.currentToken.spelling);
        currentToken = scanner.scan();
    }
    
    private Identificador parseIdentificador() throws IOException{
        Identificador idAST;
        
        if(this.currentToken.kind != scanner.map.get("id")){
            throw new Error(currentToken);       
        }else{
            idAST = new identificadorSimples(this.currentToken);
            currentToken = scanner.scan();
        }
        
        return idAST;
    }
    
    private Programa parsePrograma() throws IOException{
        Identificador idAST;
        Corpo cAST;
        
        accept("program");
        idAST = parseIdentificador();
        accept(";");
        cAST = parseCorpo();
        accept(".");
        System.out.println("funfou");
        return new Programa(idAST, cAST);
    }
    
    private Corpo parseCorpo() throws IOException{
        Declaracao dAST;
        Comando cAST;
        
        dAST = parseDeclaracoes();
        cAST = parseComandoComposto(); 
        
        return new Corpo(dAST, cAST);
    }
    
    private Declaracao parseDeclaracoes() throws IOException{
        Declaracao d1AST = null;
        
        if(this.currentToken.kind == scanner.map.get("var")){
            d1AST = parseDeclaracaoDeVariavel();
            accept(";");
        }
        
        while(this.currentToken.kind == scanner.map.get("var")){
            Declaracao d2AST = parseDeclaracaoDeVariavel();
            accept(";");
            d1AST = new declaracaoSequencial(d1AST, d2AST);
        }
        
        return d1AST;
    }
    
    private Declaracao parseDeclaracaoDeVariavel() throws IOException{
        Identificador idAST;
        Tipo tAST;
        
        accept("var");
        idAST = parseListaDeIds();
        accept(":");
        tAST = parseTipo();
        return new daclaracaoDeVariavel(idAST, tAST);
    }
   
    private Identificador parseListaDeIds() throws IOException{
        Identificador id1AST, id2AST;
        
        id1AST = parseIdentificador();
        
        while(this.currentToken.kind == scanner.map.get(",")){
            accept(",");
            id2AST = parseIdentificador();
            id1AST = new idSequencial(id1AST, id2AST);
        }
        
        return id1AST;
    }
    
    private Tipo parseTipo() throws IOException{
        Tipo tAST;
        
        if(this.currentToken.kind == scanner.map.get("array")){
            tAST = parseTipoAgregado();
        } else {
            tAST = parseTipoSimples();
        }
        
        return tAST;
    }
    
    private Tipo parseTipoSimples() throws IOException{
        Tipo tAST;
        
        if (
            this.currentToken.kind == scanner.map.get("integer") 
            || this.currentToken.kind == scanner.map.get("real") 
            || this.currentToken.kind == scanner.map.get("boolean")
        ) {
            tAST = new tipoSimples(currentToken);
            currentToken = scanner.scan();

        } else {
           throw new Error(currentToken);
        }
        
        return tAST;       
    }
    
    private Tipo parseTipoAgregado() throws IOException{
        Tipo tASt;
        Literal l1AST;
        Literal l2AST;
        
        accept("array");
        accept("[");
        l1AST = parseLiteral();
        accept("..");
        l2AST =parseLiteral();
        accept("]");
        accept("of");
        tASt = parseTipo();
        
        return new tipoAgregado(l1AST, l2AST, tASt);
    }
    
    private Literal parseLiteral() throws IOException{
        Literal lAST;
        
        if(
            this.currentToken.kind == scanner.map.get("int-lit") ||
            this.currentToken.kind == scanner.map.get("float-lit") ||
             this.currentToken.kind == scanner.map.get("bool-lit")) 
        {
            System.out.println("aq =" + currentToken.spelling);
            lAST = new Literal(currentToken);
            currentToken = scanner.scan();
        } else {
            throw new Error(currentToken);
        }
        
        return lAST;
    }
      
    private Comando parseComandoComposto() throws IOException{
        accept("begin");
        parseListaDeComandos();
        accept("end");
        return new Comando();
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
        
        System.out.println("aq " +  currentToken.kind);
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
        System.out.println("teste");
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
        while(this.currentToken.kind == scanner.map.get("*")
            || this.currentToken.kind == scanner.map.get("/")
            || this.currentToken.kind == scanner.map.get("and"))
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
           throw new Error(currentToken);
        }
    }
    
    private void parseFator() throws IOException{
       // System.out.println(this.currentToken.spelling);
        
        if(this.currentToken.kind == scanner.map.get("id")){
            parseVariavel();
        }else{
            if(this.currentToken.kind == scanner.map.get("(")){
                acceptIt();
                parseExpressao();
                accept(")");
            }else{
                if(this.currentToken.kind == scanner.map.get("bool-lit")
                   || this.currentToken.kind == scanner.map.get("float-lit")
                   || this.currentToken.kind == scanner.map.get("int-lit"))
                {
                    parseLiteral();
                }else{
                    System.out.println(currentToken.spelling);
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
