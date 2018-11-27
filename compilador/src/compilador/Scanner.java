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

/**
 *
 * @author victor
 */
public class Scanner {
    
    public Scanner() throws FileNotFoundException, IOException{
     
        BufferedReader reader = new BufferedReader(new FileReader("/home/victor/Compilador-Mini-Pascal/compilador/exemplo.txt"));
        String line = reader.readLine();
        
        while(line != null){
            System.out.println(line);
            line = reader.readLine();           
        }
    
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
    
}
