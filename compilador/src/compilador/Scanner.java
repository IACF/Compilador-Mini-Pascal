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
    Map<String, Byte> map;
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
            put(":=" , (byte) 0);
            put("boolean",(byte) 1);
            put("integer",(byte) 2);
            put("begin",(byte) 3);
            put("end",(byte) 4);
            put("if", (byte) 5);
            put("then", (byte) 6);
            put("else", (byte) 7);
            put("var", (byte) 8);
            put(":", (byte) 9);
            put(";", (byte) 10);        
            put("int-lit", (byte) 11);
            put("(", (byte) 12);
            put(")", (byte) 13);
            put(".", (byte) 14);
            put("id", (byte) 15);
            put("while", (byte) 16);
            put("do", (byte) 17);
            put(",", (byte) 18);
            put("+", (byte) 19);
            put("-", (byte) 20);
            put("or", (byte) 21);
            put("*", (byte) 22);
            put("/", (byte) 23);
            put("and", (byte) 24);
            put("<", (byte) 25);
            put(">", (byte) 26);
            put("<=", (byte) 27);
            put(">=", (byte) 28);
            put("=", (byte) 29);
            put("<>", (byte) 30);
            put("[", (byte) 31);
            put("]", (byte) 32);
            put("array", (byte) 33);
            put("..", (byte) 34);
            put("of", (byte) 35);
            put("program", (byte) 36);
            put("float-lit", (byte) 37);
            put("error", (byte) 38);
            put("eof", (byte) 39);
            put("integer", (byte) 40);
            put("real", (byte) 41);
            put("true", (byte) 42);
            put("false", (byte) 43);
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
            
            case ' ': case '\t': 
                do{
                    this.currentChar = ( char ) this.reader.read();
                    this.coordinates[1]++;
                }while(this.currentChar == ' ' || this.currentChar == '\t');
                
                break;
            case '\n':
                
                this.currentChar = ( char )reader.read();
                this.coordinates[0]++;
                this.coordinates[1] = 0;
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
                                 
            if(this.map.containsKey(this.currentSpelling)){
                return this.map.get(this.currentSpelling);
            }
                        
            return this.map.get("id");      
        }
        
        if(isDigit(this.currentChar)){
            takeIt();
            while(isDigit(this.currentChar) || this.currentChar == '.'){
                if (this.currentChar == '.') {
                    char aux = ( char )reader.read();
                                      
                    take('.');
                    
                    if(isDigit(aux)){
                        this.currentSpelling = this.currentSpelling.concat(Character.toString(aux));
                        this.coordinates[1]++;
                    }else{
                        flag = true;
                        System.out.println(aux);
                        if(aux == '.'){              
                            break;
                        }  
                        this.currentChar = aux;
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
                
                if(currentChar == '.'){
                  takeIt();
                  return this.map.get(this.currentSpelling);  
                }
                
                if(isDigit(currentChar)){
                    while(isDigit(this.currentChar))
                        takeIt();
                    return this.map.get("float-lit");
                }
                return this.map.get(this.currentSpelling); 
                                               
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
        
        while (this.currentChar == ' ' || this.currentChar == '!' || this.currentChar == '\n')
            scanSeparator();
 
        this.currentKind = scanToken();
        int collum = this.coordinates[1] - this.currentSpelling.length();
        if(collum < 0)
            collum = 1;
        return new Token(this.map, this.currentKind, this.currentSpelling, this.coordinates[0], collum);
            
    }
}
