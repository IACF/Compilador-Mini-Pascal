/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor
 */
public class Scanner {
    BufferedReader reader;
    Map<String, Integer> map;
    String currentSpelling;
    int coordinates[] = new int[2];
    char currentChar;
    int currentKind;
    boolean flag;
    
    public Scanner(BufferedReader reader) throws IOException{
       this.reader = reader;
       this.currentSpelling = "";
       currentChar = ( char )reader.read();
       this.flag = false;
       
       this.map = new HashMap<>()
        {{
            put(":=" , 0);
            put("true", 1);
            put("false", 2);
            put("begin", 3);
            put("end", 4);
            put("if", 5);
            put("then", 6);
            put("else", 7);
            put("var", 8);
            put(":", 9);
            put(";", 10);        
            put("int-lit", 11);
            put("(", 12);
            put(")", 13);
            put(".", 14);
            put("id", 15);
            put("while", 16);
            put("do", 17);
            put(",", 18);
            put("+", 19);
            put("-", 20);
            put("or", 21);
            put("*", 22);
            put("/", 23);
            put("and", 24);
            put("<", 25);
            put(">", 26);
            put("<=", 27);
            put(">=", 28);
            put("=", 29);
            put("<>", 30);
            put("[", 31);
            put("]", 32);
            put("array", 33);
            put("..", 34);
            put("of", 35);
            put("program", 36);
            put("eol", 37);
            put("error", 38);
            put("eof", 39);
            put("float-lit", 40);
        }};
       
       this.coordinates[0] = 1;
       this.coordinates[1] = 1;
    }
      
    private boolean isLetter(char c){
        return Character.isLetter(c);
    }
    
    private boolean isDigit(char c){
        return Character.isDigit(c);
    }
    
    private boolean isGraphic(char c){
        return c != '\n' && c != (char) -1;
    }
    
    private void scanSeparator() throws IOException{
        switch (this.currentChar) {
            case '!' :
               do{
                    this.coordinates[1]++;
                    this.currentChar = ( char ) this.reader.read();
                }while(isGraphic(this.currentChar));
                
                break;
            
            case ' ': 
                do{
                    this.currentChar = ( char ) this.reader.read();
                    this.coordinates[1]++;
                }while(this.currentChar == ' ');
        }
    }
       
    private void take(char c) throws IOException{
        if(this.currentChar == c){
            this.currentSpelling = currentSpelling.concat(Character.toString(this.currentChar));
            this.currentChar = ( char )reader.read();   //Here you go
            this.coordinates[1]++;
        }
    }
    
    private void takeIt() throws IOException{
        this.currentSpelling = this.currentSpelling.concat(Character.toString(this.currentChar));
        this.currentChar = ( char )reader.read();   //Here you go
        this.coordinates[1]++;
    }
    
    private int scanToken() throws IOException{
        if(isLetter(this.currentChar)){
            takeIt(); 
            while(isLetter(this.currentChar) || isDigit(this.currentChar)){
                takeIt();
            }
                        
            return this.map.get("id");      
        }
        
        if(isDigit(this.currentChar)){
            takeIt();
            while(isDigit(this.currentChar) || this.currentChar == '.'){
                if (this.currentChar == '.') {
                    char aux = ( char )reader.read();
                    
                    if(aux == '.'){
                        flag = true;
                        break;
                    }                    
                    take('.');
                    
                    if(isDigit(aux)){
                        this.currentSpelling = this.currentSpelling.concat(Character.toString(aux));
                        this.coordinates[1]++;
                    }else{
                        this.currentChar = aux;
                        flag = true;
                    }
                    
                    while(isDigit(this.currentChar)){
                        takeIt();
                    }
                    return this.map.get("float-lit");
                }
                takeIt();
            }
            return this.map.get("int-lit");
        }
        
        switch(this.currentChar){
            case '<':
                takeIt();
                
                if(this.currentChar == '>'){
                    take('>');
                }else{
                    take('=');
                }
                
                return this.map.get(this.currentSpelling);
                
            case ':': case '>':
                takeIt();
                take('=');
                
                return this.map.get(this.currentSpelling);
            
            case '.':
                takeIt();
                if(this.currentSpelling.equals(".."))
                  return this.map.get(this.currentSpelling);  
                
                if(isDigit(currentChar)){
                    while(isDigit(this.currentChar))
                        takeIt();
                    return this.map.get("float-lit");
                }
                                           
            case '\n':
                takeIt();
                this.coordinates[0]++;
                this.currentSpelling = "eol";
                return this.map.get("eol");
            
            case (char) -1:
                this.currentSpelling = "eof";
                return this.map.get("eof");
        }
        
        if(this.map.containsKey(Character.toString(this.currentChar))){
            takeIt();
            return this.map.get(this.currentSpelling);
        }
        
        takeIt();
        return this.map.get("error");
    }
    
    public Token scan() throws IOException{
        this.currentSpelling = "";
        
        if(currentKind == 37)
            this.coordinates[1] = 1;
        
        if(flag){
            this.currentSpelling = Character.toString(this.currentChar);
            this.flag = false;
        }
        
        while (this.currentChar == ' ' || this.currentChar == '!')
            scanSeparator();
 
        this.currentKind = scanToken();
        int collum = this.coordinates[1] - this.currentSpelling.length();
        if(collum < 0)
            collum = 1;
        return new Token(this.map, this.currentKind, this.currentSpelling, this.coordinates[0], collum);
            
    }
}
