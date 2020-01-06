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
    HashTable map = new HashTable();
    String currentSpelling;
    int coordinates[] = new int[2];
    char currentChar;
    int currentKind;
    boolean flag;
    
    public Scanner(BufferedReader reader) throws IOException, Exception{
       this.reader = reader;
       this.currentSpelling = "";
       currentChar = ( char )reader.read();
       this.flag = false;
       
       this.map.put(":=" , 0);
       this.map.put("true", 1);
       this.map.put("false", 2);
       this.map.put("begin", 3);
       this.map.put("end", 4);
       this.map.put("if", 5);
       this.map.put("then", 6);
       this.map.put("else", 7);
       this.map.put("var", 8);
       this.map.put(":", 9);
       this.map.put(";", 10);        
       this.map.put("int-lit", 11);
       this.map.put("(", 12);
       this.map.put(")", 13);
       this.map.put(".", 14);
       this.map.put("id", 15);
       this.map.put("while", 16);
       this.map.put("do", 17);
       this.map.put(",", 18);
       this.map.put("+", 19);
       this.map.put("-", 20);
       this.map.put("or", 21);
       this.map.put("*", 22);
       this.map.put("/", 23);
       this.map.put("and", 24);
       this.map.put("<", 25);
       this.map.put(">", 26);
       this.map.put("<=", 27);
       this.map.put(">=", 28);
       this.map.put("=", 29);
       this.map.put("<>", 30);
       this.map.put("[", 31);
       this.map.put("]", 32);
       this.map.put("array", 33);
       this.map.put("..", 34);
       this.map.put("of", 35);
       this.map.put("program", 36);
       this.map.put("eol", 37);
       this.map.put("error", 38);
       this.map.put("eof", 39);
       this.map.put("float-lit", 40);
       
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
                                      
                    take('.');
                    
                    if(isDigit(aux)){
                        this.currentSpelling = this.currentSpelling.concat(Character.toString(aux));
                        this.coordinates[1]++;
                    }else{
                        flag = true;
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
                if(this.currentSpelling.equals(".."))
                  return this.map.get(this.currentSpelling);  

                if(isDigit(currentChar)){
                    while(isDigit(this.currentChar))
                        takeIt();
                    return this.map.get("float-lit");
                }
                return this.map.get(this.currentSpelling); 
                                           
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
