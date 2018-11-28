/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor
 */
public class Scanner {
    
    Map<String, Integer> map;
    String currentSpelling;
    String line;
    int coordinates[] = new int[2];
    char currentChar;
    int currentKind;
    
    public Scanner(){
       this.currentSpelling = "";
        System.out.println("construtor");
       
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
        }};
       
       this.coordinates[0] = 0;
       this.coordinates[1] = 0;
    }
      
    private boolean isLetter(char c){
        return Character.isLetter(c);
    }
    
    private boolean isDigit(char c){
        return Character.isDigit(c);
    }
    
    private boolean isGraphic(char c){
        return true;
    }
    
    
    private byte scanSeparator(){
        switch (this.currentChar) {
            case '!' : {
                this.coordinates[0]++;
                return 0;
            }
            case ' ': 
                this.coordinates[1]++;
                System.out.println("branco");
                return 1;
            case '\n' : {
                takeIt();
                return 2;
            }            
        }
        return 3;
    }
       
    private void take(char c){
        if(this.currentChar == c){
             this.currentSpelling = currentSpelling.concat(Character.toString(this.currentChar));
             this.currentChar = this.line.charAt(++this.coordinates[1]);
        }else{
            
        }
    }
    
    private void takeIt(){
        System.out.println(this.currentChar);
        this.currentSpelling = this.currentSpelling.concat(Character.toString(this.currentChar));
        this.currentChar = this.line.charAt(++this.coordinates[1]);
    }
    
    private int scanToken(){
        if(isLetter(this.currentChar)){
            takeIt(); 
            while(isLetter(this.currentChar) || isDigit(this.currentChar)){
                takeIt();
            }
                        
            return this.map.get("id");      
        }
        
        if(isDigit(currentChar)){
            takeIt();
            while(isDigit(this.currentChar))
                takeIt();
            return this.map.get("int-lit");
        }
        
        switch(this.currentChar){
            case '<':
                
                if(this.currentChar == '>'){
                    takeIt();
                    return this.map.get(this.currentSpelling);
                }
                
            case ':': case '>':
                takeIt();
              
                if(this.currentChar == '=')
                    takeIt();
                
                return this.map.get(this.currentSpelling);
            
            case '.':
                takeIt();
                
                if(this.currentChar == '.'){
                    takeIt();
                    return this.map.get(this.currentSpelling);
                }
                break;
            
            case '\n':
                this.coordinates[0]++;
                return this.map.get("eol");
                
        }
        
        if(this.map.containsKey(this.currentChar)){
            return this.map.get(this.currentChar);
        }
            
        return this.map.get("error");
    }
    
    public void changeLine(String line){
        this.line = line;
    }
    
    public Token scan(){
            int aux;
            this.currentSpelling = "";
    
            while(this.coordinates[1] < line.length()){
               currentChar = line.charAt(this.coordinates[1]);
               // System.out.println(currentChar);
               System.out.println(this.coordinates[1]);
               aux = scanSeparator();
               if(aux == 0)
                   break;
               
               if(aux == 1)
                   continue;
               
                System.out.println("passou");
               currentKind = scanToken();
               
               return new Token(this.map, this.currentKind, this.currentSpelling, this.coordinates[0], this.coordinates[1]);
            }
            this.currentKind = this.map.get("error");
            return new Token(this.map, this.currentKind, this.currentSpelling, this.coordinates[0], this.coordinates[1]);
        }
    }
