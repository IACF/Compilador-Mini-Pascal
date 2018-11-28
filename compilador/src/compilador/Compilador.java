/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author victor
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("ex.txt"));
        String line;
        Scanner s = new Scanner();
        Token t;
        List tokens =  new ArrayList();
        
        line = reader.readLine();
        s.changeLine(line);
        
        while (line != null) {
            t = s.scan();
            tokens.add(t);
            if(t.kind == 37){
                line = reader.readLine();
                s.changeLine(line);
            }
            System.out.println("voltei");
        }
        
        System.out.println(tokens.size());
    }
    
}
