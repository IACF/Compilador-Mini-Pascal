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
    int coordinates[] = new int[2];
    char currentChar;
    
    
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
        }};
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
                this.coordinates[0]++;
                return 1;
            case '\n' : {
                takeIt();
                return 2;
            }            
        }
    }
    
    private void take (char current) {
        
    }
    
    public void scan() throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader("ex.txt"));
        String line = reader.readLine();
        //chamar separator
        
        char c;
        while (line != null) {
            System.out.println(line);
            
            for (int i = 0; i < line.length(); i++) {
               c = line.charAt(i);
               
            }
            
            line = reader.readLine();  
            //chamar separator.
        }
    }
}
