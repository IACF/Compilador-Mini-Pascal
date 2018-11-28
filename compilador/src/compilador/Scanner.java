/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author victor
 */
public class Scanner {
    
    Map<String, Integer> map;
    char currentChar;
    String currentSpelling;
    int coordnates[] = new int[2];
    String line;
    
    public Scanner(){
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
            put("error", 36);
        }};
       
       this.coordnates[0] = 0;
       this.coordnates[1] = 0;
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
    
    private void scanSeparator(){
    
    }
    
    private void take(char c){
        if(this.currentChar == c){
             this.currentSpelling = currentSpelling.concat(Character.toString(this.currentChar));
             this.currentChar = this.line.charAt(++this.coordnates[1]);
        }else{
            
        }
    }
    
    private void takeIt(){
        this.currentSpelling = currentSpelling.concat(Character.toString(this.currentChar));
        this.currentChar = this.line.charAt(++this.coordnates[1]);
    }
    
    private int scanToken(){
        if(isLetter(this.currentChar)){
            takeIt(); 
            while(isLetter(this.currentChar) || isDigit(this.currentChar)){
                takeIt();
            }
            
            if(this.map.containsKey(this.currentSpelling)){
                return this.map.get(this.currentSpelling);
            }
            
            return 15;      
        }
        
        if(isDigit(currentChar)){
            
        }
    }
    
    public void scan() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader("ex.txt"));
        
        line = reader.readLine();       
        
        while (line != null) {
            this.coordnates[0]++;
            
            System.out.println(line);
            
            while(this.coordnates[1] < line.length()){
               currentChar = line.charAt(this.coordnates[1]);               
               if(scanSeparator() == 0)
                   break;
               
               if(scanSeparator() < 3)
                   continue;
               
               scanToken();
               
            }
            
            line = reader.readLine();  
        }
    }
}
